package ui.dendi.finder.favorites_domain.videos.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video

interface FavoritesVideoRepository {

    fun getFavoritesVideo(): Flow<List<Video>>

    suspend fun saveVideoToFavorites(item: Video)

    suspend fun deleteVideoFromFavorites(item: Video)

    suspend fun deleteAllVideos()
}