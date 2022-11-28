package ui.dendi.finder.favorites_presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.ResourceProvider
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.managers.DialogManager
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.core.core.multichoice.MultiChoiceHandler
import ui.dendi.finder.core.core.multichoice.MultiChoiceState
import ui.dendi.finder.favorites_domain.images.usecase.DeleteFavoriteImageUseCase
import ui.dendi.finder.favorites_domain.images.usecase.GetFavoriteImagesUseCase
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.favorites_presentation.multichoice.ImageListItem
import javax.inject.Inject

@HiltViewModel
class FavoritesImageViewModel @Inject constructor(
    private val deleteFavoriteImageUseCase: DeleteFavoriteImageUseCase,
    private val dialogManager: DialogManager,
    private val getFavoriteImagesUseCase: GetFavoriteImagesUseCase,
    private val resourceProvider: ResourceProvider,
    private val multiChoiceHandler: MultiChoiceHandler<Image>,
) : BaseViewModel() {

    //TODO no more needed ClearFavoriteImagesUseCase, because I delete images when they are checked

    private val _favoriteImagesState = MutableStateFlow<ImagesState?>(null)
    val favoriteImagesState = _favoriteImagesState.asStateFlow().filterNotNull()

    private val _needShowDeleteButton = MutableStateFlow(false)
    val needShowDeleteButton = _needShowDeleteButton.asStateFlow()

    init {
        viewModelScope.launch {
            multiChoiceHandler.setItemsFlow(viewModelScope, getFavoriteImagesUseCase())
            val combineFlow = combine(
                getFavoriteImagesUseCase.invoke(),
                multiChoiceHandler.listen(),
                ::merge
            )
            combineFlow.collectLatest {
                _favoriteImagesState.value = it
                _needShowDeleteButton.value = it.totalCheckedCount != 0
            }
        }
    }

    fun deleteFromFavoritesImage(image: ImageListItem) {
        viewModelScope.launch {
            deleteFavoriteImageUseCase(image.image)
        }
    }

    fun onImageToggle(image: ImageListItem) {
        viewModelScope.launch {
            multiChoiceHandler.toggle(image.image)
        }
    }

    fun clearAllImages() {
        dialogManager.show(
            titleResId = resourceProvider.getString(R.string.delete_all_images_title),
            messageResId = resourceProvider.getString(R.string.delete_all_saved_images_question),
            positiveAction = {
                viewModelScope.launch {
                    deleteSelectedImages()
                }
            },
        )
    }

    private fun deleteSelectedImages() {
        viewModelScope.launch {
            multiChoiceHandler.checkedItems().collect { images ->
                images.forEach { image ->
                    deleteFavoriteImageUseCase(image)
                }
            }
        }
    }

    private fun merge(images: List<Image>, multiChoiceState: MultiChoiceState<Image>): ImagesState {
        return ImagesState(
            images = images.map { image ->
                ImageListItem(image, multiChoiceState.isChecked(image))
            },
            totalCount = images.size,
            totalCheckedCount = multiChoiceState.totalCheckedCount,
            selectAllOperation = if (multiChoiceState.totalCheckedCount < images.size) {
                SelectAllOperation(R.string.select_all, multiChoiceHandler::selectAll)
            } else {
                SelectAllOperation(R.string.clear_all, multiChoiceHandler::clearAll)
            }
        )
    }

    fun selectOrClearAll() {
        viewModelScope.launch {
            _favoriteImagesState.value?.selectAllOperation?.operation?.invoke()
        }
    }

    data class ImagesState(
        val totalCount: Int,
        val totalCheckedCount: Int,
        val images: List<ImageListItem>,
        val selectAllOperation: SelectAllOperation
    )

    data class SelectAllOperation(
        val titleRes: Int,
        val operation: suspend () -> Unit,
    )
}