package ui.dendi.finder.videos_domain.usecase

import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.favorites_domain.videos.repository.FavoritesVideoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveVideoToFavoritesUseCase @Inject constructor(
    private val repository: FavoritesVideoRepository,
) {
    suspend operator fun invoke(video: Video) = repository.saveVideoToFavorites(video)
}