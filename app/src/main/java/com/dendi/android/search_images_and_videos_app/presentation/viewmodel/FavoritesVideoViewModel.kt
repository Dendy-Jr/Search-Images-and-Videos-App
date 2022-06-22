package com.dendi.android.search_images_and_videos_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendi.android.search_images_and_videos_app.domain.video.Video
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.ClearVideosUseCase
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.DeleteVideoUseCase
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesVideoViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
    private val deleteVideoUseCase: DeleteVideoUseCase,
    private val clearVideosUseCase: ClearVideosUseCase,
) : ViewModel() {

    val favoritesVideo = getVideosUseCase.getFavoritesVideos()

    fun deleteFromFavoritesVideo(video: Video) {
        viewModelScope.launch {
            deleteVideoUseCase.deleteFavorite(video)
        }
    }

    fun deleteAllFavoritesVideos() {
        viewModelScope.launch {
            clearVideosUseCase.clearAllFavorites()
        }
    }
}