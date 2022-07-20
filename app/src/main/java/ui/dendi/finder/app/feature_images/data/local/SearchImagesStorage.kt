package ui.dendi.finder.app.feature_images.data.local

import ui.dendi.finder.app.core.storage.StorageProvider
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_STORAGE = "KEY_STORAGE"
private const val SEARCH_IMAGES_QUERY = "SEARCH_IMAGES_QUERY"

@Singleton
class ImagesStorage @Inject constructor(
    storageProvider: StorageProvider,
) {

    private val storage = storageProvider.getStorage(KEY_STORAGE)

    var query: String?
        set(value) {
            storage.edit().putString(SEARCH_IMAGES_QUERY, value).apply()
        }
        get() = storage.getString(SEARCH_IMAGES_QUERY, "dog")
}