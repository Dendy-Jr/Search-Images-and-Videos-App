package ui.dendi.finder.settings_data.repository.storage

import ui.dendi.finder.core.core.storage.StorageProvider
import ui.dendi.finder.settings_domain.ItemsPosition
import javax.inject.Inject
import javax.inject.Singleton

private const val ITEMS_POSITIONING_STORAGE = "ITEMS_POSITIONING_STORAGE"
private const val ITEMS_POSITION = "ITEMS_POSITION"

@Singleton
class ItemsPositioningStorage @Inject constructor(storageProvider: StorageProvider) {

    private val storage = storageProvider.getStorage(ITEMS_POSITIONING_STORAGE)

    var layoutManager: ItemsPosition?
        set(value) {
            storage.edit().putString(ITEMS_POSITION, value.toString()).apply()
        }
        get() = toLayoutManager(
            storage.getString(
                ITEMS_POSITION,
                ItemsPosition.VERTICAL_SINGLE.toString()
            )
        )

    private fun toLayoutManager(enumName: String?): ItemsPosition? =
        ItemsPosition.values().find { it.toString() == enumName }
}