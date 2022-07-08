package com.dendi.android.search_images_and_videos_app.feature_favorites.domain.usecase

import com.dendi.android.search_images_and_videos_app.feature_videos.domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend fun clearAllFavorites() = repository.deleteAllItems()
}