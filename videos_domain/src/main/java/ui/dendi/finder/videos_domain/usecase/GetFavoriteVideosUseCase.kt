package ui.dendi.finder.videos_domain.usecase

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.favorites_domain.videos.repository.FavoritesVideoRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFavoriteVideosUseCase @Inject constructor(
    private val repository: FavoritesVideoRepository,
) {
    operator fun invoke(): Flow<List<Video>> = repository.getFavoritesVideo()
}