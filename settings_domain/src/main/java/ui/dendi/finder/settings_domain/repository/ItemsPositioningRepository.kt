package ui.dendi.finder.settings_domain.repository

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.settings_domain.ItemsPosition

interface ItemsPositioningRepository {

    suspend fun setItemsPositioning(position: ItemsPosition)

    fun getItemsPositioning(): Flow<ItemsPosition>
}