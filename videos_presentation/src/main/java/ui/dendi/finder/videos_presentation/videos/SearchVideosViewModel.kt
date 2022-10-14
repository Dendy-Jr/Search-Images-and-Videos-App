@file:OptIn(ExperimentalCoroutinesApi::class)

package ui.dendi.finder.videos_presentation.videos

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.videos_domain.usecase.InsertVideoUseCase
import ui.dendi.finder.videos_domain.usecase.SearchVideosUseCase
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.core.core.navigation.AppNavDirections
import ui.dendi.finder.videos_data.local.SearchVideosStorage
import ui.dendi.finder.videos_data.local.VideosFilterStorage
import javax.inject.Inject

@HiltViewModel
class SearchVideosViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val insertVideoUseCase: InsertVideoUseCase,
    private val searchVideosUseCase: SearchVideosUseCase,
    private val videosFilterStorage: VideosFilterStorage,
    private val storage: SearchVideosStorage,
) : BaseViewModel() {

    private val _searchBy = MutableStateFlow(storage.query)
    val searchBy = _searchBy.asStateFlow()

    private val videosType = videosFilterStorage.getType
    private val videosCategory = videosFilterStorage.getCategory
    private val videosOrder = videosFilterStorage.getOrder

    var videosFlow = MutableStateFlow<PagingData<Video>>(PagingData.empty())

    init {
        viewModelScope.launch {
            videosResult()
        }
    }

    private suspend fun videosResult() = combine(
        videosType,
        videosCategory,
        videosOrder,
        ::videosMerge
    ).collectLatest {
        it.collectLatest { pagingData ->
            videosFlow.value = pagingData
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

    fun searchVideo(query: String) {
        if (_searchBy.value == query) return
        storage.query = query
        _searchBy.value = storage.query
    }

    fun addToFavorite(video: Video) {
        viewModelScope.launch {
            insertVideoUseCase(video)
        }
    }

    fun launchDetailsScreen(video: Video) {
        navigateTo(appNavDirections.videoDetailsScreen(video))
    }
}