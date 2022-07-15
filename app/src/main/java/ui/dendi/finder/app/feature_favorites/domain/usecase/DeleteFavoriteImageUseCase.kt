package ui.dendi.finder.app.feature_favorites.domain.usecase

import ui.dendi.finder.app.feature_favorites.domain.FavoritesImageRepository
import ui.dendi.finder.app.feature_images.domain.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteFavoriteImageUseCase @Inject constructor(
    private val repository: FavoritesImageRepository,
) {
    suspend operator fun invoke(image: Image) {
        repository.deleteImage(image)
    }
}