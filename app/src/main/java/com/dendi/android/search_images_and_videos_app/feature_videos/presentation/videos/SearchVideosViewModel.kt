package com.dendi.android.search_images_and_videos_app.feature_videos.presentation.videos

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dendi.android.search_images_and_videos_app.app.navigation.AppNavDirections
import com.dendi.android.search_images_and_videos_app.app.navigation.Navigator
import com.dendi.android.search_images_and_videos_app.core.base.BaseViewModel
import com.dendi.android.search_images_and_videos_app.feature_videos.data.local.SearchVideosStorage
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.Video
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.usecase.InsertVideoUseCase
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.usecase.SearchVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchVideosViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val insertVideoUseCase: InsertVideoUseCase,
    private val navigator: Navigator,
    private val searchVideosUseCase: SearchVideosUseCase,
    private val storage: SearchVideosStorage,
) : BaseViewModel() {

    private val searchBy = MutableStateFlow(storage.query)

    val videosFlow = searchBy.flatMapLatest { query ->
        searchVideosUseCase.searchVideo(query)
    }.cachedIn(viewModelScope)

    fun searchVideo(query: String) {
        if (searchBy.value == query) return
        storage.query = query
        searchBy.value = storage.query
    }

    fun addToFavorite(video: Video) {
        viewModelScope.launch {
            insertVideoUseCase.addToFavorite(video)
        }
    }

    fun launchDetailsScreen(video: Video) {
        navigator.navigateTo(appNavDirections.videoDetailsScreen(video))
    }
}