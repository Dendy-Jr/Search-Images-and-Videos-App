package ui.dendi.finder.favorites_domain.videos.usecase

import ui.dendi.finder.videos_domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearFavoriteVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke() = repository.deleteAllVideos()
}