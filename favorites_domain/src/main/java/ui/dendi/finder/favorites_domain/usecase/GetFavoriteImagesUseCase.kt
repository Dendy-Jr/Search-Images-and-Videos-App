package ui.dendi.finder.favorites_domain.usecase

import ui.dendi.finder.favorites_domain.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFavoriteImagesUseCase @Inject constructor(
    private val repository: FavoritesImageRepository,
) {
    operator fun invoke() = repository.getFavoritesImage()
}