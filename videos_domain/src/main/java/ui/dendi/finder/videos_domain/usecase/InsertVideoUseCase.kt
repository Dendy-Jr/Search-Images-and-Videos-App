package ui.dendi.finder.videos_domain.usecase

import ui.dendi.finder.videos_domain.VideosRepository
import ui.dendi.finder.core.core.models.Video
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InsertVideoUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke(video: Video) = repository.saveVideoToFavorites(video)
}