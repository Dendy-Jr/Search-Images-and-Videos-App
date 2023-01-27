package ui.dendi.finder.settings_presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.settings_domain.ItemsPosition
import ui.dendi.finder.settings_domain.use_case.SetItemsPositioningUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logger: Logger,
    private val setItemsPositioningUseCase: SetItemsPositioningUseCase,
) : BaseViewModel(logger) {

    fun setItemsPositioning(position: ItemsPosition) {
        viewModelScope.launch {
            setItemsPositioningUseCase(position)
        }
    }
}