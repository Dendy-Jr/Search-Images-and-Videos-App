package com.dendi.android.search_images_and_videos_app.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.domain.image.ImagesRepository
import kotlinx.coroutines.launch


/**
 * @author Dendy-Jr on 27.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesImageViewModel(
    private val imagesRepository: ImagesRepository,
) : ViewModel() {

    val favoritesImage = imagesRepository.getImages()

    fun deleteFromFavoritesImage(image: ImageEntity) {
        viewModelScope.launch {
            imagesRepository.deleteImage(image)
        }
    }
}