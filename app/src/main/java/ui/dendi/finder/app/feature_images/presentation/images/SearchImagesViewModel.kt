package ui.dendi.finder.app.feature_images.presentation.images

import androidx.lifecycle.*
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import ui.dendi.finder.app.navigation.AppNavDirections
import ui.dendi.finder.app.core.base.BaseViewModel
import ui.dendi.finder.app.feature_images.data.local.ImagesStorage
import ui.dendi.finder.app.feature_images.domain.Image
import ui.dendi.finder.app.feature_images.domain.usecase.InsertImageUseCase
import ui.dendi.finder.app.feature_images.domain.usecase.LocalImagesUseCase
import ui.dendi.finder.app.feature_images.domain.usecase.SearchImagesUseCase
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val searchImageUseCase: SearchImagesUseCase,
    private val insertImageUseCase: InsertImageUseCase,
//    private val localImagesUseCase: LocalImagesUseCase,
    private val storage: ImagesStorage,
) : BaseViewModel() {

    private val _searchBy = MutableStateFlow(storage.query)
    val searchBy = _searchBy.asStateFlow()
    private val _scrollList = MutableStateFlow(Unit)
    val scrollList = _scrollList.asStateFlow()

//    init {
//        viewModelScope.launch {
//            localImagesUseCase.invoke().collectLatest {
//                Timber.d("list -> $it, size -> ${it.size}")
//            }
//        }
//    }

    val imagesFlow = _searchBy
        .flatMapLatest { query ->
            searchImageUseCase(query ?: "")
        }.cachedIn(viewModelScope)

    fun setSearchBy(query: String) {
        if (_searchBy.value == query) return
        storage.query = query
        _searchBy.value = storage.query
//        scrollListToTop()
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