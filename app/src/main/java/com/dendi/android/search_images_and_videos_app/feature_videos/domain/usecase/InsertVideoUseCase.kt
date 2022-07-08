package com.dendi.android.search_images_and_videos_app.feature_videos.domain.usecase

import com.dendi.android.search_images_and_videos_app.feature_videos.domain.Video
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertVideoUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend fun addToFavorite(video: Video) = repository.insertItem(video)
}