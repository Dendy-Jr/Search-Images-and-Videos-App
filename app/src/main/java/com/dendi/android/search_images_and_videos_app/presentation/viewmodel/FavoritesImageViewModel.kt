package com.dendi.android.search_images_and_videos_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.ClearFavoritesImagesUseCase
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.DeleteFavoriteImageUseCase
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.GetFavoritesImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesImageViewModel @Inject constructor(
    private val getImagesUseCase: GetFavoritesImageUseCase,
    private val deleteImageUseCase: DeleteFavoriteImageUseCase,
    private val clearImagesUseCase: ClearFavoritesImagesUseCase,
) : ViewModel() {

    val favoritesImage = getImagesUseCase.getFavoritesImages()

    fun deleteFromFavoritesImage(image: Image) {
        viewModelScope.launch {
            deleteImageUseCase.deleteFavorite(image)
        }
    }

    fun deleteAllFavoritesImages() {
        viewModelScope.launch {
            clearImagesUseCase.clearAllFavorites()
        }
    }
}