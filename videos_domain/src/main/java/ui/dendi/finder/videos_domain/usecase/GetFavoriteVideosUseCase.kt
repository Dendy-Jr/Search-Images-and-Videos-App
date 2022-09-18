package ui.dendi.finder.videos_domain.usecase

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.videos_domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFavoriteVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    operator fun invoke(): Flow<List<Video>> = repository.getItems()
}