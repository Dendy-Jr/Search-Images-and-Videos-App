package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import com.dendi.android.search_images_and_videos_app.domain.image.repository.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFavoritesImageUseCase @Inject constructor(
    private val repository: FavoritesImageRepository,
) {
    fun getFavoritesImages() = repository.getFavoritesImage()
}