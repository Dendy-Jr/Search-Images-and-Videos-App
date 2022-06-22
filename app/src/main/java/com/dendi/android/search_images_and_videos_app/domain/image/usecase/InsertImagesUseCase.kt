package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    suspend fun saveImagesToFavorites(images: List<Image>) = repository.insertAllItems(images)
}