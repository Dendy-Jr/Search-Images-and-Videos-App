package ui.dendi.finder.settings_data.repository.storage

import ui.dendi.finder.core.core.models.ListColumnType
import ui.dendi.finder.core.core.storage.StorageProvider
import javax.inject.Inject
import javax.inject.Singleton

private const val ITEMS_POSITIONING_STORAGE = "ITEMS_POSITIONING_STORAGE"
private const val ITEMS_POSITION = "ITEMS_POSITION"

@Singleton
class ItemsPositioningStorage @Inject constructor(storageProvider: StorageProvider) {

    private val storage = storageProvider.getStorage(ITEMS_POSITIONING_STORAGE)

    var layoutManager: ListColumnType?
        set(value) {
            storage.edit().putString(ITEMS_POSITION, value.toString()).apply()
        }
        get() = toLayoutManager(
            storage.getString(
                ITEMS_POSITION, ListColumnType.ONE_COLUMN.toString()
            )
        )

    private fun toLayoutManager(enumName: String?): ListColumnType? =
        ListColumnType.values().find { it.toString() == enumName }
}