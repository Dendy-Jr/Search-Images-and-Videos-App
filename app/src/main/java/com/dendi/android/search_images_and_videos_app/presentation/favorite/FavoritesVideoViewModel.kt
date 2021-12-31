package com.dendi.android.search_images_and_videos_app.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoEntity
import com.dendi.android.search_images_and_videos_app.domain.video.VideosRepository
import kotlinx.coroutines.launch


/**
 * @author Dendy-Jr on 27.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesVideoViewModel(
    private val videosRepository: VideosRepository
) : ViewModel() {

    val favoritesVideo = videosRepository.getVideos()

    fun deleteFromFavoritesVideo(image: VideoEntity) {
        viewModelScope.launch {
            videosRepository.deleteVideo(image)
        }
    }
}