package ui.dendi.finder.videos_domain.usecase

import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.videos_domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke(videos: List<Video>) = repository.insertAllItems(videos)
}