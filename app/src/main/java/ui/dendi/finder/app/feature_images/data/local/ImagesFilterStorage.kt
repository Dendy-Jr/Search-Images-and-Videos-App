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

    private companion object {
        private const val KEY_STORAGE = "KEY_STORAGE"
        private const val IMAGES_TYPE = "IMAGES_TYPE"
        private const val IMAGES_CATEGORY = "IMAGES_CATEGORY"
        private const val DEFAULT_TYPE = "all"
        private const val DEFAULT_CATEGORY = "sports"
    }
}