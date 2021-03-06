package ui.dendi.finder.app.feature_videos.domain.usecase

import ui.dendi.finder.app.feature_videos.domain.Video
import ui.dendi.finder.app.feature_videos.domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke(videos: List<Video>) = repository.insertAllItems(videos)
}