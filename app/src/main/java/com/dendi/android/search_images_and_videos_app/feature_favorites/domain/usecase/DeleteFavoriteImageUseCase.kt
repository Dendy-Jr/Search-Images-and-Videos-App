package com.dendi.android.search_images_and_videos_app.feature_favorites.domain.usecase

import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import com.dendi.android.search_images_and_videos_app.feature_favorites.domain.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteFavoriteImageUseCase @Inject constructor(
    private val repository: FavoritesImageRepository,
) {
    suspend fun deleteFavorite(image: Image) {
        repository.deleteImage(image)
    }
}