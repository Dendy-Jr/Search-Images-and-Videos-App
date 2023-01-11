package ui.dendi.finder.images_presentation.images

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.core.core.multichoice.ImageListItem
import ui.dendi.finder.core.core.multichoice.MultiChoiceHandler
import ui.dendi.finder.core.core.multichoice.MultiChoiceState
import ui.dendi.finder.core.core.navigation.AppNavDirections
import ui.dendi.finder.images_data.local.ImagesFilterStorage
import ui.dendi.finder.images_data.local.ImagesStorage
import ui.dendi.finder.images_domain.repository.MultiChoiceImagesRepository
import ui.dendi.finder.images_domain.usecase.SaveImageToFavoritesUseCase
import ui.dendi.finder.images_domain.usecase.SearchImagesUseCase
import ui.dendi.finder.images_presentation.R
import java.time.LocalDateTime
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    imagesFilterStorage: ImagesFilterStorage,
    private val saveImageToFavoritesUseCase: SaveImageToFavoritesUseCase,
    private val searchImageUseCase: SearchImagesUseCase,
    private val storage: ImagesStorage,
    private val multiChoiceHandler: MultiChoiceHandler<Image>,
    logger: Logger,
    private val multiChoiceImagesRepository: MultiChoiceImagesRepository
) : BaseViewModel(logger) {

    private val _searchBy = MutableStateFlow(storage.query)
    val searchBy = _searchBy.asStateFlow()

    private val imageCategory = imagesFilterStorage.getCategory
    private val imageColors = imagesFilterStorage.getColors
    private val imageOrientation = imagesFilterStorage.getOrientation
    private val imageType = imagesFilterStorage.getType

    private val _imagesPagingData = MutableStateFlow<PagingData<Image>>(PagingData.empty())

    private val _imagesState = MutableStateFlow<ImagesState?>(null)
    val imagesState = _imagesState.asStateFlow()

    private val _multiChoiceImagesSize = MutableStateFlow(0)

    private val _needShowAddToFavoriteButton = MutableStateFlow(false)
    val needShowAddToFavoriteButton get() = _needShowAddToFavoriteButton

    init {
        preload()
    }

    private suspend fun imagesResult() = combine(
        imageType,
        imageCategory,
        imageOrientation,
        imageColors,
        ::imagesMerge
    ).collectLatest {
        it.collectLatest { pagingData ->
            _imagesPagingData.value = pagingData
        }
    }

    private fun imagesMerge(
        type: String,
        category: String,
        orientation: String,
        colors: String,
    ): Flow<PagingData<Image>> = _searchBy.flatMapLatest { query ->
        searchImageUseCase(
            query = query ?: "",
            type = type,
            category = category,
            orientation = orientation,
            colors = colors,
        )
    }.cachedIn(viewModelScope)

    private suspend fun merge(
        images: PagingData<Image>,
        multiChoiceState: MultiChoiceState<Image>,
    ): ImagesState {
        return ImagesState(
            pagingData = images.map { image ->
                ImageListItem(image, multiChoiceState.isChecked(image))
            },
            selectAllOperation = SelectAllOperation(
                R.string.clear_all,
                multiChoiceHandler::clearAll
            ),
        )
    }

    fun preload() {
        viewModelScope.launch {
            imagesResult()
        }

        viewModelScope.launch {
            multiChoiceHandler.setItemsFlow(
                viewModelScope,
                multiChoiceImagesRepository.getMultiChoiceImages(),
            )
            val combineFlow = combine(
                _imagesPagingData,
                multiChoiceHandler.listen(),
                ::merge
            )
            combineFlow.collectLatest {
                _imagesState.value = it
            }
        }

        viewModelScope.launch {
            multiChoiceImagesRepository.getMultiChoiceImages().collectLatest {
                _multiChoiceImagesSize.value = it.size
            }
        }
    }

    fun setSearchBy(query: String) {
        viewModelScope.launch {
            if (_searchBy.value == query) return@launch
            storage.query = query
            _searchBy.value = storage.query

            //TODO delete in future
            multiChoiceImagesRepository.deleteAllMultiChoiceImages()
        }
    }

    fun addToFavorite(images: ImageListItem) {
        viewModelScope.launch {
            val date = LocalDateTime.now()
            saveImageToFavoritesUseCase(images.image.copy(date = date.toString()))
        }
    }

    fun launchDetailScreen(images: ImageListItem) {
        navigateTo(appNavDirections.imageDetailsScreen(images.image))
    }

    fun onImageToggle(images: ImageListItem) {
        viewModelScope.launch {
            multiChoiceHandler.toggle(images.image)
            showOrNotFavoriteButton()
        }
    }

    fun clearAllMultiChoiceImages() {
        viewModelScope.launch {
            _imagesState.value?.selectAllOperation?.operation?.invoke()
            showOrNotFavoriteButton()
        }
    }

    fun addCheckedToFavorites() {
        viewModelScope.launch {
            multiChoiceHandler.checkedItems().collectLatest {
                it.forEach {
                    val date = LocalDateTime.now()
                    saveImageToFavoritesUseCase(it.copy(date = date.toString()))
                }
            }
        }
    }

    private suspend fun showOrNotFavoriteButton() {
        multiChoiceHandler.checkedItems().collectLatest {
            _needShowAddToFavoriteButton.value = it.isNotEmpty()
        }
    }

    data class ImagesState(
        val pagingData: PagingData<ImageListItem>,
        val selectAllOperation: SelectAllOperation,
    )

    data class SelectAllOperation(
        val titleRes: Int,
        val operation: suspend () -> Unit,
    )

    override fun onCleared() {
        super.onCleared()
        viewModelScope.launch {
            multiChoiceImagesRepository.deleteAllMultiChoiceImages()
        }
    }
}