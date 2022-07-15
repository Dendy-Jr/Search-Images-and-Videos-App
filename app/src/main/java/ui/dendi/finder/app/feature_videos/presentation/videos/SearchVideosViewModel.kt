package ui.dendi.finder.app.feature_videos.presentation.videos

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import ui.dendi.finder.app.navigation.AppNavDirections
import ui.dendi.finder.app.core.base.BaseViewModel
import ui.dendi.finder.app.feature_videos.data.local.SearchVideosStorage
import ui.dendi.finder.app.feature_videos.domain.Video
import ui.dendi.finder.app.feature_videos.domain.usecase.InsertVideoUseCase
import ui.dendi.finder.app.feature_videos.domain.usecase.SearchVideosUseCase
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchVideosViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val insertVideoUseCase: InsertVideoUseCase,
    private val searchVideosUseCase: SearchVideosUseCase,
    private val storage: SearchVideosStorage,
) : BaseViewModel() {

    private val searchBy = MutableStateFlow(storage.query)

    val videosFlow = searchBy.flatMapLatest { query ->
        searchVideosUseCase(query ?: "")
    }.cachedIn(viewModelScope)

    fun searchVideo(query: String) {
        if (searchBy.value == query) return
        storage.query = query
        searchBy.value = storage.query
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