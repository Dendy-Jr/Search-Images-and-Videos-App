package ui.dendi.android.finder.feature_favorites.domain.usecase

import ui.dendi.android.finder.feature_videos.domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    suspend operator fun invoke() = repository.deleteAllItems()
}