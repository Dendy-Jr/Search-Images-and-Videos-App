package com.dendi.android.search_images_and_videos_app.feature_images.presentation.images

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.dendi.android.search_images_and_videos_app.app.navigation.AppNavDirections
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
import timber.log.Timber
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val searchImageUseCase: SearchImagesUseCase,
    private val insertImageUseCase: InsertImageUseCase,
    private val localImagesUseCase: LocalImagesUseCase,
    private val storage: ImagesStorage,
) : BaseViewModel() {

    private val searchBy = MutableStateFlow(storage.query)
    private val _scrollList = MutableStateFlow(Unit)
    val scrollList = _scrollList.asStateFlow()

    init {
        viewModelScope.launch {
            localImagesUseCase.invoke().collectLatest {
                Timber.d("list -> $it, size -> ${it.size}")
            }
        }
    }

    val imagesFlow = searchBy
        .flatMapLatest { query ->
            searchImageUseCase(query ?: "")
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
            insertImageUseCase(image)
        }
    }

    fun launchDetailScreen(image: Image) {
        navigateTo(appNavDirections.imageDetailsScreen(image))
    }
}