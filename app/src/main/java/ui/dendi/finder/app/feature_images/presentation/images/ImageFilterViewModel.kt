package ui.dendi.finder.app.feature_images.presentation.images

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import ui.dendi.finder.app.core.base.BaseViewModel
import ui.dendi.finder.app.feature_images.data.local.ImagesFilterStorage
import javax.inject.Inject

@HiltViewModel
class ImageFilterViewModel @Inject constructor(
    private val storage: ImagesFilterStorage,
) : BaseViewModel() {

    fun setType(type: String) {
        viewModelScope.launch {
            Timber.d(type)
            storage.setType(type)
        }
    }

    fun setCategory(type: String) {
        viewModelScope.launch {
            Timber.d(type)
            storage.setCategory(type)
        }
    }

    fun setOrientation(orientation: String) {
        viewModelScope.launch {
            Timber.d(orientation)
            storage.setOrientation(orientation)
        }
    }

    fun setColors(colors: String) {
        viewModelScope.launch {
            Timber.d(colors)
            storage.setColors(colors)
        }
    }
}