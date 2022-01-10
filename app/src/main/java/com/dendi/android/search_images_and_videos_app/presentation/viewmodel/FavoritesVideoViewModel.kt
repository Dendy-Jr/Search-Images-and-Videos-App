package com.dendi.android.search_images_and_videos_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.ClearVideosUseCase
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.DeleteVideoUseCase
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.GetVideosUseCase
import kotlinx.coroutines.launch


/**
 * @author Dendy-Jr on 27.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesVideoViewModel(
    private val getVideosUseCase: GetVideosUseCase,
    private val deleteVideoUseCase: DeleteVideoUseCase,
    private val clearVideosUseCase: ClearVideosUseCase
) : ViewModel() {

    val favoritesVideo = getVideosUseCase.getFavoritesVideos()

    fun deleteFromFavoritesVideo(video: VideoCache) {
        viewModelScope.launch {
            deleteVideoUseCase.deleteFavorite(video)
        }
    }
    fun deleteAllFavoritesVideos()  {
        viewModelScope.launch {
            clearVideosUseCase.clearAllFavorites()
        }
    }
}