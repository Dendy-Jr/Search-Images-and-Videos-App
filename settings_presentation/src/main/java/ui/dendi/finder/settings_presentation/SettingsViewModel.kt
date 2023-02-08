package ui.dendi.finder.settings_presentation

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.models.ImagesColumnType
import ui.dendi.finder.core.core.models.VideosColumnType
import ui.dendi.finder.settings_domain.use_case.SetImagesPositioningUseCase
import ui.dendi.finder.settings_domain.use_case.SetVideosPositioningUseCase
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    logger: Logger,
    private val setItemsPositioningUseCase: SetImagesPositioningUseCase,
    private val setVideosPositioningUseCase: SetVideosPositioningUseCase,
) : BaseViewModel(logger) {

    fun setItemsPositioning(type: ImagesColumnType) {
        viewModelScope.launch {
            setItemsPositioningUseCase(type)
        }
    }

    fun setVideosPositioning(type: VideosColumnType) {
        viewModelScope.launch {
            setVideosPositioningUseCase(type)
        }
    }
}