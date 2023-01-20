package ui.dendi.finder.videos_domain.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.Video

interface MultiChoiceVideosRepository {

    fun getMultiChoiceVideos(): Flow<List<Video>>

    suspend fun insertVideos(list: List<Video>)

    suspend fun deleteAllMultiChoiceVideos()
}