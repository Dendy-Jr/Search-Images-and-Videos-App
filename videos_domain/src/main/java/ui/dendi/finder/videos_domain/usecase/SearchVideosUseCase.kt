package ui.dendi.finder.videos_domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.videos_domain.repository.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    operator fun invoke(
        category: String?,
        order: String?,
        query: String,
        type: String?,
    ): Flow<PagingData<Video>> = repository.getPagedVideos(
        category = category,
        order = order,
        query = query,
        type = type,
    )
}