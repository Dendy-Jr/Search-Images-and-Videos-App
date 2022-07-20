package ui.dendi.finder.app.feature_videos.data.local

import ui.dendi.finder.app.core.storage.StorageProvider
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_STORAGE = "KEY_STORAGE"
private const val SEARCH_VIDEOS_QUERY = "SEARCH_VIDEOS_QUERY"

@Singleton
class SearchVideosStorage @Inject constructor(
    storageProvider: StorageProvider,
) {
    private val storage = storageProvider.getStorage(KEY_STORAGE)

    var query: String?
        set(value) {
            storage.edit().putString(SEARCH_VIDEOS_QUERY, value).apply()
        }
        get() = storage.getString(SEARCH_VIDEOS_QUERY, "cat")
}