package ui.dendi.finder.favorites_presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.ResourceProvider
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.managers.DialogManager
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.core.core.multichoice.MultiChoiceHandler
import ui.dendi.finder.core.core.multichoice.MultiChoiceState
import ui.dendi.finder.core.core.multichoice.VideoListItem
import ui.dendi.finder.favorites_domain.videos.usecase.DeleteFavoriteVideoUseCase
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.videos_domain.usecase.GetFavoriteVideosUseCase
import javax.inject.Inject

@HiltViewModel
class FavoritesVideoViewModel @Inject constructor(
    private val deleteFavoriteVideoUseCase: DeleteFavoriteVideoUseCase,
    private val dialogManager: DialogManager,
    private val getFavoriteVideosUseCase: GetFavoriteVideosUseCase,
    private val resourceProvider: ResourceProvider,
    private val multiChoiceHandler: MultiChoiceHandler<Video>,
    logger: Logger,
) : BaseViewModel(logger) {

    //TODO no more needed ClearFavoriteVideosUseCase, because I delete images when they are checked

    private val _favoriteVideos = MutableStateFlow<VideosState?>(null)
    val favoriteVideos = _favoriteVideos.asStateFlow().filterNotNull()

    private val _needShowDeleteButton = MutableStateFlow(false)
    val needShowDeleteButton = _needShowDeleteButton.asStateFlow()

    init {
        preload()
    }

    fun preload() {
        viewModelScope.launch {
            multiChoiceHandler.setItemsFlow(viewModelScope, getFavoriteVideosUseCase.invoke())
            val combineFlow = combine(
                getFavoriteVideosUseCase.invoke(),
                multiChoiceHandler.listen(),
                ::merge
            )
            combineFlow.collectLatest {
                _favoriteVideos.value = it
                _needShowDeleteButton.value = it.totalCheckedCount != 0
            }
        }
    }

    fun deleteFromFavoritesVideo(video: VideoListItem) {
        viewModelScope.launch {
            deleteFavoriteVideoUseCase(video.video)
        }
    }

    fun onVideoToggle(video: VideoListItem) {
        viewModelScope.launch {
            multiChoiceHandler.toggle(video.video)
        }
    }

    fun clearAllVideos() {
        dialogManager.show(
            titleResId = resourceProvider.getString(R.string.delete_all_videos_title),
            messageResId = resourceProvider.getString(R.string.delete_all_saved_videos_question),
            positiveAction = {
                viewModelScope.launch {
                    deleteSelectedVideos()
                }
            }
        )
    }

    private fun deleteSelectedVideos() {
        viewModelScope.launch {
            multiChoiceHandler.checkedItems().collect { videos ->
                videos.forEach { video ->
                    deleteFavoriteVideoUseCase(video)
                }
            }
        }
    }

    private fun merge(videos: List<Video>, multiChoiceState: MultiChoiceState<Video>): VideosState {
        return VideosState(
            videos = videos.map { video ->
                VideoListItem(video, multiChoiceState.isChecked(video))
            },
            totalCount = videos.size,
            totalCheckedCount = multiChoiceState.totalCheckedCount,
            selectAllOperation = if (multiChoiceState.totalCheckedCount < videos.size) {
                SelectAllOperation(R.string.select_all, multiChoiceHandler::selectAll)
            } else {
                SelectAllOperation(R.string.clear_all, multiChoiceHandler::clearAll)
            }
        )
    }

    fun selectOrClearAll() {
        viewModelScope.launch {
            _favoriteVideos.value?.selectAllOperation?.operation?.invoke()
        }
    }

    data class VideosState(
        val totalCount: Int,
        val totalCheckedCount: Int,
        val videos: List<VideoListItem>,
        val selectAllOperation: SelectAllOperation
    )

    data class SelectAllOperation(
        val titleRes: Int,
        val operation: suspend () -> Unit,
    )
}