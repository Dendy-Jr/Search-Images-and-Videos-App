package ui.dendi.finder.settings_data.repository.storage

import ui.dendi.finder.core.core.models.ImagesColumnType
import ui.dendi.finder.core.core.storage.StorageProvider
import javax.inject.Inject
import javax.inject.Singleton

private const val IMAGES_POSITIONING_STORAGE = "IMAGES_POSITIONING_STORAGE"
private const val IMAGES_POSITION = "IMAGES_POSITION"

@Singleton
class ImagesPositioningStorage @Inject constructor(storageProvider: StorageProvider) {

    private val storage = storageProvider.getStorage(IMAGES_POSITIONING_STORAGE)

    var layoutManager: ImagesColumnType?
        set(value) {
            storage.edit().putString(IMAGES_POSITION, value.toString()).apply()
        }
        get() = toLayoutManager(
            storage.getString(
                IMAGES_POSITION, ImagesColumnType.ONE_COLUMN.toString()
            )
        )

    private fun toLayoutManager(enumName: String?): ImagesColumnType? =
        ImagesColumnType.values().find { it.toString() == enumName }
}