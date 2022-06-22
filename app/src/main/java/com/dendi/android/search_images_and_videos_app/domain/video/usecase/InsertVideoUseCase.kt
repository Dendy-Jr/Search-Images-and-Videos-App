package com.dendi.android.search_images_and_videos_app.domain.video.usecase

import com.dendi.android.search_images_and_videos_app.domain.video.Video
import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertVideoUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend fun saveVideoToFavorite(video: Video) = repository.insertItem(video)
}