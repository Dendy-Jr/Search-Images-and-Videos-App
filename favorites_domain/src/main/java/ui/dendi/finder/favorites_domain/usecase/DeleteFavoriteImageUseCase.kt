package ui.dendi.finder.favorites_domain.usecase

import ui.dendi.finder.favorites_domain.FavoritesImageRepository
import ui.dendi.finder.core.core.models.Image
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