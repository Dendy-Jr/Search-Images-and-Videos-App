package ui.dendi.finder.settings_domain.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.VideosColumnType

interface VideosPositioningRepository {

    suspend fun setVideosPositioning(type: VideosColumnType)
    fun getVideosPositioning(): Flow<VideosColumnType>
}