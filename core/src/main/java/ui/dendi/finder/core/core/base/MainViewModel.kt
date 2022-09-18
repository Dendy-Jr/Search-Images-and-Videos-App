package ui.dendi.finder.core.core.base

import dagger.hilt.android.lifecycle.HiltViewModel
import ui.dendi.finder.core.core.navigation.AppNavDirections
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
) : BaseViewModel()