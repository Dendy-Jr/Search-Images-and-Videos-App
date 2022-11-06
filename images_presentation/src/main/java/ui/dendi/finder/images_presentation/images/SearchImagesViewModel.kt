package ui.dendi.finder.images_presentation.images

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.images_data.local.ImagesFilterStorage
import ui.dendi.finder.images_data.local.ImagesStorage
import ui.dendi.finder.images_domain.usecase.SaveImageToFavoritesUseCase
import ui.dendi.finder.images_domain.usecase.SearchImagesUseCase
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.core.core.navigation.AppNavDirections
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val imagesFilterStorage: ImagesFilterStorage,
    private val saveImageToFavoritesUseCase: SaveImageToFavoritesUseCase,
    private val searchImageUseCase: SearchImagesUseCase,
    private val storage: ImagesStorage,
) : BaseViewModel() {

    private val _searchBy = MutableStateFlow(storage.query)
    val searchBy = _searchBy.asStateFlow()

    private val imageCategory = imagesFilterStorage.getCategory
    private val imageColors = imagesFilterStorage.getColors
    private val imageOrientation = imagesFilterStorage.getOrientation
    private val imageType = imagesFilterStorage.getType

    var imagesFlow = MutableStateFlow<PagingData<Image>>(PagingData.empty())
        private set

    init {
        viewModelScope.launch {
            imagesResult()
        }
    }

    private suspend fun imagesResult() = combine(
        imageType,
        imageCategory,
        imageOrientation,
        imageColors,
        ::imagesMerge
    ).collectLatest {
        it.collectLatest { pagingData ->
            imagesFlow.value = pagingData
        }
    }

    private fun imagesMerge(
        type: String,
        category: String,
        orientation: String,
        colors: String,
    ) = _searchBy
        .flatMapLatest { query ->
            searchImageUseCase(
                query = query ?: "",
                type = type,
                category = category,
                orientation = orientation,
                colors = colors,
            )
        }.cachedIn(viewModelScope)

    fun setSearchBy(query: String) {
        if (_searchBy.value == query) return
        storage.query = query
        _searchBy.value = storage.query
    }

    fun addToFavorite(image: Image) {
        viewModelScope.launch {
            saveImageToFavoritesUseCase(image)
        }
    }

    fun launchDetailScreen(image: Image) {
        navigateTo(appNavDirections.imageDetailsScreen(image))
    }
}