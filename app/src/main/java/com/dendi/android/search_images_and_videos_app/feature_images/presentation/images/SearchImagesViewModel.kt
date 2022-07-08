package com.dendi.android.search_images_and_videos_app.feature_images.presentation.images

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.dendi.android.search_images_and_videos_app.app.navigation.AppNavDirections
import com.dendi.android.search_images_and_videos_app.app.navigation.Navigator
import com.dendi.android.search_images_and_videos_app.core.base.BaseViewModel
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImagesStorage
import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import com.dendi.android.search_images_and_videos_app.feature_images.domain.usecase.InsertImageUseCase
import com.dendi.android.search_images_and_videos_app.feature_images.domain.usecase.LocalImagesUseCase
import com.dendi.android.search_images_and_videos_app.feature_images.domain.usecase.SearchImagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val searchImageUseCase: SearchImagesUseCase,
    private val insertImageUseCase: InsertImageUseCase,
    private val localImagesUseCase: LocalImagesUseCase,
    private val navigator: Navigator,
    private val storage: ImagesStorage,
) : BaseViewModel() {

    private val searchBy = MutableStateFlow(storage.query)
    private val _scrollList = MutableStateFlow(Unit)
    val scrollList = _scrollList.asStateFlow()

    val localImages = localImagesUseCase.getImages()

    val imagesFlow = searchBy
        .flatMapLatest { query ->
            searchImageUseCase.searchImages(query ?: "")
        }.cachedIn(viewModelScope)

    fun setSearchBy(query: String) {
        if (searchBy.value == query) return
        storage.query = query
        searchBy.value = storage.query
        scrollListToTop()
    }

    private fun scrollListToTop() {
        _scrollList.value = Unit
    }

    fun addToFavorite(image: Image) {
        viewModelScope.launch {
            insertImageUseCase.saveImageToFavorites(image)
        }
    }

    fun launchDetailScreen(image: Image) {
        navigator.navigateTo(appNavDirections.imageDetailsScreen(image))
    }
}