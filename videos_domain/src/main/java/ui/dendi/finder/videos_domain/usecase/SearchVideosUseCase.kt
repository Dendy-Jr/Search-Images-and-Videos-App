package ui.dendi.finder.videos_domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.videos_domain.VideosRepository
import ui.dendi.finder.core.core.models.Video
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
     operator fun invoke(query: String): Flow<PagingData<Video>> = repository.getPagedItems(query)
}