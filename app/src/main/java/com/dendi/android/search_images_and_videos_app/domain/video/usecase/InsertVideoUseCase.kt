package com.dendi.android.search_images_and_videos_app.domain.video.usecase

import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository

/**
 * @author Dendy-Jr on 02.01.2022
 * olehvynnytskyi@gmail.com
 */
class InsertVideoUseCase(private val repository: VideosRepository) {
    suspend fun saveVideoToFavorite(video: VideoCache) = repository.insertVideo(video)
}