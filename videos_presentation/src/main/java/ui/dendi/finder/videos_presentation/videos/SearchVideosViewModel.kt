@file:OptIn(ExperimentalCoroutinesApi::class)

package ui.dendi.finder.videos_presentation.videos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.core.core.multichoice.MultiChoiceHandler
import ui.dendi.finder.core.core.multichoice.MultiChoiceState
import ui.dendi.finder.core.core.multichoice.VideoListItem
import ui.dendi.finder.core.core.navigation.AppNavDirections
import ui.dendi.finder.settings_domain.use_case.GetImagesPositioningUseCase
import ui.dendi.finder.videos_data.local.SearchVideosStorage
import ui.dendi.finder.videos_data.local.VideosFilterStorage
import ui.dendi.finder.videos_domain.repository.MultiChoiceVideosRepository
import ui.dendi.finder.videos_domain.usecase.SaveVideoToFavoritesUseCase
import ui.dendi.finder.videos_domain.usecase.SearchVideosUseCase
import ui.dendi.finder.videos_presentation.R
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class SearchVideosViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val saveVideoToFavoritesUseCase: SaveVideoToFavoritesUseCase,
    private val getItemsPositioningUseCase: GetImagesPositioningUseCase,
    private val searchVideosUseCase: SearchVideosUseCase,
    private val searchVideosStorage: SearchVideosStorage,
    private val multiChoiceHandler: MultiChoiceHandler<Video>,
    private val multiChoiceVideosRepository: MultiChoiceVideosRepository,
    videosFilterStorage: VideosFilterStorage,
    logger: Logger,
) : BaseViewModel(logger) {

    private val _searchBy = MutableStateFlow(searchVideosStorage.query)
    val searchBy = _searchBy.asStateFlow()

    private val videosType = videosFilterStorage.getType
    private val videosCategory = videosFilterStorage.getCategory
    private val videosOrder = videosFilterStorage.getOrder

    private val videosPagingData = MutableStateFlow<PagingData<Video>>(PagingData.empty())

    private val _videosState = MutableStateFlow<VideosState?>(null)
    val videosState = _videosState.asStateFlow().filterNotNull()

    private val _needShowAddToFavoriteButton = MutableStateFlow(false)
    val needShowAddToFavoriteButton = _needShowAddToFavoriteButton.asStateFlow()

    private val _listColumnType = getItemsPositioningUseCase.invoke()
    val listColumnType = _listColumnType

    init {
        preload()
    }

    private suspend fun videosResult() = combine(
        videosType,
        videosCategory,
        videosOrder,
        ::videosMerge
    ).collectLatest {
        it.collectLatest { pagingData ->
            videosPagingData.value = pagingData
        }
    }

    private fun videosMerge(
        type: String,
        category: String,
        order: String
    ) = _searchBy
        .flatMapLatest { query ->
            searchVideosUseCase(
                query = query ?: "",
                type = type,
                category = category,
                order = order,
            )
        }.cachedIn(viewModelScope)

    private suspend fun merge(
        videos: PagingData<Video>,
        multiChoiceState: MultiChoiceState<Video>,
    ): VideosState {
        return VideosState(
            pagingData = videos.map { video ->
                VideoListItem(video, multiChoiceState.isChecked(video))
            },
            selectAllOperation = SelectAllOperation(
                titleRes = R.string.clear_all,
                operation = multiChoiceHandler::clearAll,
            ),
        )
    }

    fun preload() {
        viewModelScope.launch {
            videosResult()
        }

        viewModelScope.launch {
            multiChoiceHandler.setItemsFlow(
                viewModelScope,
                multiChoiceVideosRepository.getMultiChoiceVideos(),
            )
            val combineFlow = combine(
                videosPagingData,
                multiChoiceHandler.listen(),
                ::merge
            )
            combineFlow.collectLatest {
                _videosState.value = it
            }
        }
    }

    fun setSearchBy(query: String) {
        viewModelScope.launch {
            if (_searchBy.value == query) return@launch
            searchVideosStorage.query = query
            _searchBy.value = searchVideosStorage.query

            //TODO delete in future
            multiChoiceVideosRepository.deleteAllMultiChoiceVideos()
        }
    }

    fun launchDetailsScreen(videoItem: VideoListItem) {
        navigateTo(appNavDirections.videoDetailsScreen(videoItem.video))
    }

    fun onVideoToggle(videoItem: VideoListItem) {
        viewModelScope.launch {
            multiChoiceHandler.toggle(videoItem.video)
            showOrNotFavoriteButton()

            Timber.d(_needShowAddToFavoriteButton.value.toString())
        }
    }

    fun clearAllMultiChoiceVideos() {
        viewModelScope.launch {
            _videosState.value?.selectAllOperation?.operation?.invoke()
            showOrNotFavoriteButton()
        }
    }

    fun addCheckedToFavorites() {
        viewModelScope.launch {
            multiChoiceHandler.checkedItems().collectLatest {
                it.forEach {
                    val date = LocalDateTime.now()
                    saveVideoToFavoritesUseCase(it.copy(date = date.toString()))
                }
            }
        }
    }

    private suspend fun showOrNotFavoriteButton() {
        multiChoiceHandler.checkedItems().collectLatest {
            _needShowAddToFavoriteButton.value = it.isNotEmpty()
        }
    }

    data class VideosState(
        val pagingData: PagingData<VideoListItem>,
        val selectAllOperation: SelectAllOperation,
    )

    data class SelectAllOperation(
        val titleRes: Int,
        val operation: suspend () -> Unit,
    )

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            multiChoiceVideosRepository.deleteAllMultiChoiceVideos()
        }
    }
}