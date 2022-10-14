package ui.dendi.finder.images_presentation.images

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.images_data.local.ImagesFilterStorage
import javax.inject.Inject

@HiltViewModel
class ImageFilterViewModel @Inject constructor(
    private val storage: ImagesFilterStorage,
) : BaseViewModel() {

    fun setType(type: String?) {
        viewModelScope.launch {
            storage.setType(type ?: "")
        }
    }

    fun setCategory(type: String?) {
        viewModelScope.launch {
            storage.setCategory(type ?: "")
        }
    }

    fun setOrientation(orientation: String?) {
        viewModelScope.launch {
            storage.setOrientation(orientation ?: "")
        }
    }

    fun setColors(colors: String?) {
        viewModelScope.launch {
            storage.setColors(colors ?: "")
        }
    }

    fun clearImagesFilter() {
        setType(null)
        setCategory(null)
        setOrientation(null)
        setColors(null)
    }
}