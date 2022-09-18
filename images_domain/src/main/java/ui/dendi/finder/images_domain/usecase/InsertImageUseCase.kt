package ui.dendi.finder.images_domain.usecase

import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.favorites_domain.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertImageUseCase @Inject constructor(
    private val repository: FavoritesImageRepository,
) {
    suspend operator fun invoke(image: Image) = repository.insertImage(image)
}