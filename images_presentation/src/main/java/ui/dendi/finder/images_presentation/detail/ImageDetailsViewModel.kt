package ui.dendi.finder.images_presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.images_domain.usecase.SaveImageToFavoritesUseCase
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ImageDetailsViewModel @Inject constructor(
    private val saveImageToFavoritesUseCase: SaveImageToFavoritesUseCase,
    savedStateHandle: SavedStateHandle,
    logger: Logger,
) : BaseViewModel(logger) {

    private val args = ImageDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    fun saveImageToFavorites() {
        viewModelScope.launch {
            val date = LocalDateTime.now()
            saveImageToFavoritesUseCase(args.image.copy(date = date.toString()))
        }
    }
}