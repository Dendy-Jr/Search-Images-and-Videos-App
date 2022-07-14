package ui.dendi.android.finder.feature_videos.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.android.finder.feature_videos.domain.Video
import ui.dendi.android.finder.feature_videos.domain.VideosRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
     operator fun invoke(query: String): Flow<PagingData<Video>> = repository.getPagedItems(query)
}