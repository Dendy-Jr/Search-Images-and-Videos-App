package ui.dendi.finder.videos_presentation.videos

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.videos_data.local.VideosFilterStorage
import javax.inject.Inject

@HiltViewModel
class VideoFilterViewModel @Inject constructor(
    private val storage: VideosFilterStorage,
    logger: Logger,
) : BaseViewModel(logger) {

    fun setType(type: String?) {
        viewModelScope.launch {
            storage.setType(type ?: "")
        }
    }

    fun setCategory(category: String?) {
        viewModelScope.launch {
            storage.setCategory(category ?: "")
        }
    }

    fun setOrder(order: String?) {
        viewModelScope.launch {
            storage.setOrder(order ?: "")
        }
    }

    fun clearVideosFilter() {
        setType(null)
        setCategory(null)
        setOrder(null)
    }
}