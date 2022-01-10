package com.dendi.android.search_images_and_videos_app.domain.video.usecase

import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository

/**
 * @author Dendy-Jr on 02.01.2022
 * olehvynnytskyi@gmail.com
 */
class ClearVideosUseCase(private val repository: VideosRepository) {
    suspend fun clearAllFavorites() = repository.deleteAllVideos()
}