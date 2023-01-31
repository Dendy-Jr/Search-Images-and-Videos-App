package ui.dendi.finder.settings_domain.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.ListColumnType

interface ItemsPositioningRepository {

    suspend fun setItemsPositioning(position: ListColumnType)

    fun getItemsPositioning(): Flow<ListColumnType>
}