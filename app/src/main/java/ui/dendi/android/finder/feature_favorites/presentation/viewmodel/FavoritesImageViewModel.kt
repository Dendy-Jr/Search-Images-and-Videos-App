package ui.dendi.android.finder.feature_favorites.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import ui.dendi.android.finder.core.base.BaseViewModel
import ui.dendi.android.finder.core.managers.DialogManager
import ui.dendi.android.finder.feature_favorites.domain.usecase.ClearFavoriteImagesUseCase
import ui.dendi.android.finder.feature_favorites.domain.usecase.DeleteFavoriteImageUseCase
import ui.dendi.android.finder.feature_favorites.domain.usecase.GetFavoriteImagesUseCase
import ui.dendi.android.finder.feature_images.domain.Image
import javax.inject.Inject

@HiltViewModel
class FavoritesImageViewModel @Inject constructor(
    private val dialogManager: DialogManager,
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val deleteFavoriteImageUseCase: DeleteFavoriteImageUseCase,
    private val clearFavoriteImagesUseCase: ClearFavoriteImagesUseCase,
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

    fun deleteAllImages() {
        dialogManager.show(
            titleResId = "Delete all images",
            messageResId = "Are you sure you want to delete all saved images?",
            positiveAction = {
                viewModelScope.launch {
                    clearFavoriteImagesUseCase()
                }
            },
        )
    }

    private fun getFavoriteImages() {
        viewModelScope.launch {
            getFavoriteImagesUseCase()
                .collect { result ->
                    result.onSuccess {
                        Timber.d(it.toString())
                        _favoriteImages.value = it
                        _needShowDeleteButton.value = it.isNotEmpty()
                    }.onFailure {
                        Timber.d("Failed")
                    }
                }
        }
    }
}