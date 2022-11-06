package ui.dendi.finder.favorites_presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.ErrorHandler
import ui.dendi.finder.core.core.ResourceProvider
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.managers.DialogManager
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.favorites_domain.images.usecase.ClearFavoriteImagesUseCase
import ui.dendi.finder.favorites_domain.images.usecase.DeleteFavoriteImageUseCase
import ui.dendi.finder.favorites_domain.images.usecase.GetFavoriteImagesUseCase
import ui.dendi.finder.favorites_presentation.R
import javax.inject.Inject

@HiltViewModel
class FavoritesImageViewModel @Inject constructor(
    private val clearAllFavoriteImagesUseCase: ClearFavoriteImagesUseCase,
    private val deleteFavoriteImageUseCase: DeleteFavoriteImageUseCase,
    private val dialogManager: DialogManager,
    private val errorHandler: ErrorHandler,
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val resourceProvider: ResourceProvider,
) : BaseViewModel() {

    private val _favoriteImages = MutableStateFlow<List<Image>>(emptyList())
    val favoriteImages = _favoriteImages.asStateFlow()

    private val _needShowDeleteButton = MutableStateFlow(false)
    val needShowDeleteButton = _needShowDeleteButton.asStateFlow()

    init {
        getFavoriteImages()
    }

    fun deleteFromFavoritesImage(image: Image) {
        viewModelScope.launch {
            deleteFavoriteImageUseCase(image)
        }
    }

    fun clearAllImages() {
        dialogManager.show(
            titleResId = resourceProvider.getString(R.string.delete_all_images_title),
            messageResId = resourceProvider.getString(R.string.delete_all_saved_images_question),
            positiveAction = {
                viewModelScope.launch {
                    clearAllFavoriteImagesUseCase()
                }
            },
        )
    }

    private fun getFavoriteImages() {
        viewModelScope.launch {
            getFavoriteImagesUseCase()
                .collect { result ->
                    result.onSuccess {
                        _favoriteImages.value = it
                        _needShowDeleteButton.value = it.isNotEmpty()
                    }.onFailure { throwable ->
                        errorHandler.onError(throwable)
                    }
                }
        }
    }
}