package ui.dendi.finder.favorites_presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.managers.DialogManager
import ui.dendi.finder.favorites_domain.usecase.ClearFavoriteImagesUseCase
import ui.dendi.finder.favorites_domain.usecase.DeleteFavoriteImageUseCase
import ui.dendi.finder.favorites_domain.usecase.GetFavoriteImagesUseCase
import ui.dendi.finder.core.core.models.Image
import javax.inject.Inject

@HiltViewModel
class FavoritesImageViewModel @Inject constructor(
    private val dialogManager: DialogManager,
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val deleteFavoriteImageUseCase: DeleteFavoriteImageUseCase,
    private val clearAllFavoriteImagesUseCase: ClearFavoriteImagesUseCase,
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
    // TODO Don't hard code values
    fun clearAllImages() {
        dialogManager.show(
            titleResId = "Delete all images",
            messageResId = "Are you sure you want to delete all saved images?",
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
                    }.onFailure {
                        Timber.d("Failed")
                    }
                }
        }
    }
}