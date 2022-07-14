package ui.dendi.android.finder.core.storage

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageProvider @Inject constructor(
    @ApplicationContext private val context: Context,
) {

    fun getStorage(key: String): SharedPreferences {
        return context.getSharedPreferences(key, Context.MODE_PRIVATE)
    }
}