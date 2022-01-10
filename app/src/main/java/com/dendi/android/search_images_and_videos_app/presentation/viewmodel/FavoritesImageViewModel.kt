package com.dendi.android.search_images_and_videos_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.ClearImagesUseCase
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.DeleteImageUseCase
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.GetImagesUseCase
import kotlinx.coroutines.launch


/**
 * @author Dendy-Jr on 27.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesImageViewModel(
    private val getImagesUseCase: GetImagesUseCase,
    private val deleteImageUseCase: DeleteImageUseCase,
    private val clearImagesUseCase: ClearImagesUseCase
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