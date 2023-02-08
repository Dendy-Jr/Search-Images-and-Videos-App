package ui.dendi.finder.settings_data.repository.storage

import ui.dendi.finder.core.core.models.VideosColumnType
import ui.dendi.finder.core.core.storage.StorageProvider
import javax.inject.Inject
import javax.inject.Singleton

private const val VIDEOS_POSITIONING_STORAGE = "VIDEOS_POSITIONING_STORAGE"
private const val VIDEOS_POSITION = "VIDEOS_POSITION"

@Singleton
class VideosPositioningStorage @Inject constructor(storageProvider: StorageProvider) {

    private val storage = storageProvider.getStorage(VIDEOS_POSITIONING_STORAGE)

    var layoutManager: VideosColumnType?
        set(value) {
            storage.edit().putString(VIDEOS_POSITION, value.toString()).apply()
        }
        get() = toLayoutManager(
            storage.getString(
                VIDEOS_POSITION, VideosColumnType.ONE_COLUMN.toString()
            )
        )

    private fun toLayoutManager(enumName: String?): VideosColumnType? =
        VideosColumnType.values().find { it.toString() == enumName }
}