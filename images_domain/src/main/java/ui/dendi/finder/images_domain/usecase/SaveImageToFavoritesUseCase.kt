package ui.dendi.finder.images_domain.usecase

import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.favorites_domain.images.repository.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveImageToFavoritesUseCase @Inject constructor(
    private val repository: FavoritesImageRepository,
) {
    suspend operator fun invoke(image: Image) = repository.insertImage(image)
}