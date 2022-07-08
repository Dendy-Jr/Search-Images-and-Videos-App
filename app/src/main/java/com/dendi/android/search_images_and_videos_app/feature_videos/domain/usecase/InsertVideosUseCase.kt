package com.dendi.android.search_images_and_videos_app.feature_videos.domain.usecase

import com.dendi.android.search_images_and_videos_app.feature_videos.domain.Video
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend fun saveVideosToFavorites(videos: List<Video>) = repository.insertAllItems(videos)
}