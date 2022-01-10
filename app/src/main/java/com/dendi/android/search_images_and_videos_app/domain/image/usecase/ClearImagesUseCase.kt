package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository

/**
 * @author Dendy-Jr on 02.01.2022
 * olehvynnytskyi@gmail.com
 */
class ClearImagesUseCase(private val repository: ImagesRepository) {
    suspend fun clearAllFavorites() = repository.deleteAllImages()
}