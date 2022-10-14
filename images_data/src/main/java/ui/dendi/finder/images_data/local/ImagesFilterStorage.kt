package ui.dendi.finder.images_data.local

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
class ImagesFilterStorage @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(IMAGES_KEY_STORAGE)

    private val imagesType = stringPreferencesKey(IMAGES_TYPE)
    private val imagesCategory = stringPreferencesKey(IMAGES_CATEGORY)
    private val imagesOrientation = stringPreferencesKey(IMAGES_ORIENTATION)
    private val imagesColors = stringPreferencesKey(IMAGES_COLORS)

    val getType: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[imagesType] ?: DEFAULT_TYPE
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
            preferences[imagesCategory] ?: DEFAULT_CATEGORY
        }

    val getOrientation: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[imagesOrientation] ?: DEFAULT_ORIENTATION
        }

    val getColors: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[imagesColors] ?: DEFAULT_COLORS
        }

    suspend fun setType(type: String) {
        context.dataStore.edit {
            it[imagesType] = type
        }
    }

    suspend fun setCategory(category: String) {
        context.dataStore.edit {
            it[imagesCategory] = category
        }
    }

    suspend fun setOrientation(orientation: String) {
        context.dataStore.edit {
            it[imagesOrientation] = orientation
        }
    }

    suspend fun setColors(colors: String) {
        context.dataStore.edit {
            it[imagesColors] = colors
        }
    }

    private companion object {
        private const val IMAGES_KEY_STORAGE = "IMAGES_KEY_STORAGE"
        private const val IMAGES_TYPE = "IMAGES_TYPE"
        private const val IMAGES_CATEGORY = "IMAGES_CATEGORY"
        private const val IMAGES_ORIENTATION = "IMAGES_ORIENTATION"
        private const val IMAGES_COLORS = "IMAGES_COLORS"

        private const val DEFAULT_TYPE = "all"
        private const val DEFAULT_CATEGORY = "sports"
        private const val DEFAULT_ORIENTATION = "all"
        private const val DEFAULT_COLORS = ""
    }
}