package com.dendi.android.search_images_and_videos_app.feature_images.domain.usecase

import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import com.dendi.android.search_images_and_videos_app.feature_images.domain.repository.ImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    suspend operator fun invoke(images: List<Image>) = repository.insertAllItems(images)
}