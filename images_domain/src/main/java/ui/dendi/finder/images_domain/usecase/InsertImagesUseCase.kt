package ui.dendi.finder.images_domain.usecase

import ui.dendi.finder.images_domain.repository.ImagesRepository
import ui.dendi.finder.core.core.models.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    suspend operator fun invoke(images: List<Image>) = repository.insertAllItems(images)
}