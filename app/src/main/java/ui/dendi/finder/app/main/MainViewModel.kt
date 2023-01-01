package ui.dendi.finder.app.main

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import ui.dendi.finder.app.R
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.models.MainTab
import ui.dendi.finder.core.core.widget.MainTabBarItem
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    logger: Logger,
    private val storage: MainStorage,
) : BaseViewModel(logger) {

    private val _tabs = MutableStateFlow(emptyList<MainTabBarItem>())
    val tabs = _tabs.asStateFlow()

    private var mainTab: MainTab = MainTab.IMAGES
        set(value) {
            field = value
            storage.tab = value
        }

    lateinit var selectedTab: MainTabBarItem
        private set

    init {
        setTab(storage.tab ?: MainTab.IMAGES)
    }

    private fun setTab(tab: MainTab) {
        selectedTab = tab.toItem()
        _tabs.value = listOf(
            MainTab.IMAGES,
            MainTab.VIDEOS,
            MainTab.FAVORITES,
        ).map { it.toItem() }
    }

    fun onTabSelected(tab: MainTabBarItem) {
        selectedTab = tab
        mainTab = tab.origin
    }

    private fun MainTab.toItem(): MainTabBarItem = when (this) {
        MainTab.IMAGES -> MainTabBarItem(
            R.navigation.images_graph, R.drawable.ic_images, R.string.images, this
        )
        MainTab.VIDEOS -> MainTabBarItem(
            R.navigation.videos_graph, R.drawable.ic_videos, R.string.videos, this
        )
        MainTab.FAVORITES -> MainTabBarItem(
            R.navigation.favorites_graph, R.drawable.ic_favorites, R.string.favorites, this
        )
    }
}