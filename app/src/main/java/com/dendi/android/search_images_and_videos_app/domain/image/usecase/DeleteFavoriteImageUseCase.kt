package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.repository.FavoritesImageRepository
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