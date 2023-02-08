package ui.dendi.finder.settings_domain.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.ImagesColumnType

interface ImagesPositioningRepository {

    suspend fun setImagesPositioning(type: ImagesColumnType)

    fun getImagesPositioning(): Flow<ImagesColumnType>
}