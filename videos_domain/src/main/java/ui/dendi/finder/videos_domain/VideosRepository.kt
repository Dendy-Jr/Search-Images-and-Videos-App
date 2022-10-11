package ui.dendi.finder.videos_domain

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video

interface VideosRepository {

    fun getPagedVideos(
        query: String,
        type: String? = null,
        category: String? = null,
        orientation: String? = null,
        colors: String? = null
    ): Flow<PagingData<Video>>

    fun getFavoritesVideo(): Flow<List<Video>>

    suspend fun saveVideoToFavorites(item: Video)

    suspend fun deleteVideoFromFavorites(item: Video)

    suspend fun deleteAllVideos()
}
//interface VideosRepository: BaseRepository<Video>