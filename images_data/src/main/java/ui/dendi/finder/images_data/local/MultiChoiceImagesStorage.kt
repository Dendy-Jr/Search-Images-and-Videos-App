package ui.dendi.finder.images_data.local

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import timber.log.Timber
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.core.core.storage.StorageProvider
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

private const val KEY_STORAGE = "KEY_STORAGE"
private const val MULTI_CHOICE_IMAGES_STORAGE = "MULTI_CHOICE_IMAGES_STORAGE"

@Singleton
class MultiChoiceImagesStorage @Inject constructor(
    storageProvider: StorageProvider,
    private val json: Json,
    private val logger: Logger
) : Logger by logger {

    //TODO No more needed

    private val storage = storageProvider.getStorage(KEY_STORAGE)

    suspend fun updateMultiChoiceImages(images: Set<Image>) = withContext(Dispatchers.IO) {
        val raw = json.encodeToString(images)
        Timber.d(raw)
        storage.edit().putString(MULTI_CHOICE_IMAGES_STORAGE, raw).apply()
    }

    fun getMultiChoiceImages(): Flow<Set<Image>> = flow {
        try {
            val raw = storage.getString(MULTI_CHOICE_IMAGES_STORAGE, "") ?: ""
            emit(json.decodeFromString(raw))
        } catch (e: Exception) {
            log(e.message.toString())
            emit(emptySet())
        }
    }
}