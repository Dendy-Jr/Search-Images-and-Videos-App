package ui.dendi.finder.app.feature_favorites.domain.usecase

import ui.dendi.finder.app.feature_videos.domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearFavoriteVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke() = repository.deleteAllItems()
}