package ui.dendi.finder.videos_domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video

interface VideosRepository {

    fun getPagedVideos(
        category: String? = null,
        order: String? = null,
        query: String,
        type: String? = null,
    ): Flow<PagingData<Video>>
}