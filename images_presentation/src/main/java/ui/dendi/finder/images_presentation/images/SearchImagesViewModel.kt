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
import ui.dendi.finder.settings_domain.use_case.GetImagesPositioningUseCase
import java.time.LocalDateTime
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchImagesViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
    private val getImagesPositioningUseCase: GetImagesPositioningUseCase,
    private val saveImageToFavoritesUseCase: SaveImageToFavoritesUseCase,
    private val searchImageUseCase: SearchImagesUseCase,
    private val storage: ImagesStorage,
    private val multiChoiceHandler: MultiChoiceHandler<Image>,
    private val multiChoiceImagesRepository: MultiChoiceImagesRepository,
    imagesFilterStorage: ImagesFilterStorage,
    logger: Logger,
) : BaseViewModel(logger) {

    private val _searchBy = MutableStateFlow(storage.query)
    val searchBy = _searchBy.asStateFlow()

    private val imageCategory = imagesFilterStorage.getCategory
    private val imageColors = imagesFilterStorage.getColors
    private val imageOrientation = imagesFilterStorage.getOrientation
    private val imageType = imagesFilterStorage.getType

    private val imagesPagingData = MutableStateFlow<PagingData<Image>>(PagingData.empty())

    private val _imagesState = MutableStateFlow<ImagesState?>(null)
    val imagesState = _imagesState.asStateFlow()

    private val _needShowAddToFavoriteButton = MutableStateFlow(false)
    val needShowAddToFavoriteButton = _needShowAddToFavoriteButton.asStateFlow()

    private val _listColumnType = getImagesPositioningUseCase.invoke()
    val listColumnType = _listColumnType

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
            imagesPagingData.value = pagingData
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
                multiChoiceHandler::clearAll,
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
                imagesPagingData,
                multiChoiceHandler.listen(),
                ::merge
            )
            combineFlow.collectLatest {
                _imagesState.value = it
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

    fun addToFavorite(imageItem: ImageListItem) {
        viewModelScope.launch {
            val date = LocalDateTime.now()
            saveImageToFavoritesUseCase(imageItem.image.copy(date = date.toString()))
        }
    }

    fun launchDetailScreen(imageItem: ImageListItem) {
        navigateTo(appNavDirections.imageDetailsScreen(imageItem.image))
    }

    fun onImageToggle(imageItem: ImageListItem) {
        viewModelScope.launch {
            multiChoiceHandler.toggle(imageItem.image)
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