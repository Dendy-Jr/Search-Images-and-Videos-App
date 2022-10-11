package ui.dendi.finder.favorites_domain.images.usecase

import ui.dendi.finder.favorites_domain.images.repository.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearFavoriteImagesUseCase @Inject constructor(
    private val repository: FavoritesImageRepository,
) {
    suspend operator fun invoke() = repository.deleteAllImage()

}