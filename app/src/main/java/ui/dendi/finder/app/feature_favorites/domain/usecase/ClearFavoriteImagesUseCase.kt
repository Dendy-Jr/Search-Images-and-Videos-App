package ui.dendi.finder.app.feature_favorites.domain.usecase

import ui.dendi.finder.app.feature_favorites.domain.FavoritesImageRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearFavoriteImagesUseCase @Inject constructor(
    private val repository: FavoritesImageRepository,
) {
    suspend operator fun invoke() = repository.deleteAllImage()

}