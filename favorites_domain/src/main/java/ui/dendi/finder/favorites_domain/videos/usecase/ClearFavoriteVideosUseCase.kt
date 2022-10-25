package ui.dendi.finder.favorites_domain.videos.usecase

import ui.dendi.finder.favorites_domain.videos.repository.FavoritesVideoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearFavoriteVideosUseCase @Inject constructor(
    private val repository: FavoritesVideoRepository,
) {
    suspend operator fun invoke() = repository.deleteAllVideos()
}