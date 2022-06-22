package com.dendi.android.search_images_and_videos_app.domain.video.usecase

import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend fun clearAllFavorites() = repository.deleteAllItems()
}