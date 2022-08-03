package ui.dendi.finder.app.feature_images.data.local

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

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(KEY_STORAGE)

    private val imageType = stringPreferencesKey(IMAGES_TYPE)
    private val imageCategory = stringPreferencesKey(IMAGES_CATEGORY)
    private val imageOrientation = stringPreferencesKey(IMAGES_ORIENTATION)
    private val imageColors = stringPreferencesKey(IMAGES_COLORS)

    val getType: Flow<String> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            preferences[imageType] ?: DEFAULT_TYPE
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
            preferences[imageCategory] ?: DEFAULT_CATEGORY
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
            preferences[imageOrientation] ?: DEFAULT_ORIENTATION
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
            preferences[imageColors] ?: DEFAULT_COLORS
        }

    suspend fun setType(type: String) {
        context.dataStore.edit {
            it[imageType] = type
        }
    }

    suspend fun setCategory(category: String) {
        context.dataStore.edit {
            it[imageCategory] = category
        }
    }

    suspend fun setOrientation(orientation: String) {
        context.dataStore.edit {
            it[imageOrientation] = orientation
        }
    }

    suspend fun setColors(colors: String) {
        context.dataStore.edit {
            it[imageColors] = colors
        }
    }

    private companion object {
        private const val KEY_STORAGE = "KEY_STORAGE"
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