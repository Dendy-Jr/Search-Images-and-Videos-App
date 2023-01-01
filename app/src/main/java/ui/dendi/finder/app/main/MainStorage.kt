package ui.dendi.finder.app.main

import ui.dendi.finder.core.core.models.MainTab
import ui.dendi.finder.core.core.storage.StorageProvider
import javax.inject.Inject
import javax.inject.Singleton

private const val MAIN_STORAGE = "MAIN_STORAGE"
private const val MAIN_TAB_ITEM = "MAIN_TAB_ITEM"

@Singleton
class MainStorage @Inject constructor(
    storageProvider: StorageProvider,
) {

    private val storage = storageProvider.getStorage(MAIN_STORAGE)

    var tab: MainTab?
        set(value) {
            storage.edit().putString(MAIN_TAB_ITEM, value.toString()).apply()
        }
        get() = toMainTab(storage.getString(MAIN_TAB_ITEM, null))

    private fun toMainTab(enumName: String?): MainTab? =
        MainTab.values().find { it.toString() == enumName }
}