package ui.dendi.android.finder.feature_images.domain.usecase

import ui.dendi.android.finder.feature_images.domain.Image
import ui.dendi.android.finder.feature_images.domain.repository.ImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    suspend operator fun invoke(images: List<Image>) = repository.insertAllItems(images)
}