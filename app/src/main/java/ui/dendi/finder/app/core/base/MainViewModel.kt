package ui.dendi.finder.app.core.base

import dagger.hilt.android.lifecycle.HiltViewModel
import ui.dendi.finder.app.navigation.AppNavDirections
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
) : BaseViewModel() {

    init {
//        navigator.navigateTo(appNavDirections.mainScreen)
    }
}