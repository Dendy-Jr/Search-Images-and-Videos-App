package ui.dendi.finder.videos_data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosFilterStorage @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(VIDEOS_KEY_STORAGE)

    private val videosType = stringPreferencesKey(VIDEOS_TYPE)
    private val videosCategory = stringPreferencesKey(VIDEOS_CATEGORY)
    private val videosOrder = stringPreferencesKey(VIDEOS_ORDER)

    val getType: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[videosType] ?: DEFAULT_TYPE
        }

    val getCategory: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[videosCategory] ?: DEFAULT_CATEGORY
        }

    val getOrder: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[videosOrder] ?: DEFAULT_ORDER
        }

    suspend fun setType(type: String) {
        context.dataStore.edit {
            it[videosType] = type
        }
    }

    suspend fun setCategory(type: String) {
        context.dataStore.edit {
            it[videosCategory] = type
        }
    }

    suspend fun setOrder(type: String) {
        context.dataStore.edit {
            it[videosOrder] = type
        }
    }

    private companion object {
        private const val VIDEOS_KEY_STORAGE = "VIDEOS_KEY_STORAGE"
        private const val VIDEOS_TYPE = "VIDEOS_TYPE"
        private const val VIDEOS_CATEGORY = "VIDEOS_CATEGORY"
        private const val VIDEOS_ORDER = "VIDEOS_ORDER"

        private const val DEFAULT_TYPE = "all"
        private const val DEFAULT_CATEGORY = "animals"
        private const val DEFAULT_ORDER = "popular"
    }
}