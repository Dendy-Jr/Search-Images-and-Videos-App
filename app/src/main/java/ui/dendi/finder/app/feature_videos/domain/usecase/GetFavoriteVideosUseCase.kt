package ui.dendi.finder.app.feature_videos.domain.usecase

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.app.feature_videos.domain.Video
import ui.dendi.finder.app.feature_videos.domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetFavoriteVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    operator fun invoke(): Flow<List<Video>> = repository.getItems()
}