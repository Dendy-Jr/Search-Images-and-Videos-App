package ui.dendi.finder.app.feature_images.presentation.images

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.app.navigation.AppNavDirections
import ui.dendi.finder.app.core.base.BaseViewModel
import ui.dendi.finder.app.feature_images.data.local.ImagesFilterStorage
import ui.dendi.finder.app.feature_images.data.local.ImagesStorage
import ui.dendi.finder.app.feature_images.domain.Image
import ui.dendi.finder.app.feature_images.domain.usecase.InsertImageUseCase
import ui.dendi.finder.app.feature_images.domain.usecase.SearchImagesUseCase
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val searchImageUseCase: SearchImagesUseCase,
    private val insertImageUseCase: InsertImageUseCase,
    private val imagesFilterStorage: ImagesFilterStorage,
    private val storage: ImagesStorage,
) : BaseViewModel() {

    private val _searchBy = MutableStateFlow(storage.query)
    val searchBy = _searchBy.asStateFlow()

    val imageType = imagesFilterStorage.getType
    val imageCategory = imagesFilterStorage.getCategory

    val imageResult = imageType.zip(imageCategory) { a, b ->
        imagesFlow(a, b)
    }

//    val imagesFlow = _searchBy
//        .flatMapLatest { query ->
//            searchImageUseCase(query ?: "", imageType.value)
//        }.cachedIn(viewModelScope)

    fun imagesFlow(type: String, category: String) = _searchBy
        .flatMapLatest { query ->
            searchImageUseCase(query ?: "", type, category)
        }.cachedIn(viewModelScope)

    fun setSearchBy(query: String) {
        if (_searchBy.value == query) return
        storage.query = query
        _searchBy.value = storage.query
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