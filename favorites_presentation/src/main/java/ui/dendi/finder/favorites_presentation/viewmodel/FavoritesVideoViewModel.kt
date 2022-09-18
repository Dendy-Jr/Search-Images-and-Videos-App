package ui.dendi.finder.favorites_presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.dendi.finder.favorites_domain.usecase.ClearFavoriteVideosUseCase
import ui.dendi.finder.favorites_domain.usecase.DeleteFavoriteVideoUseCase
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.managers.DialogManager
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.videos_domain.usecase.GetFavoriteVideosUseCase
import javax.inject.Inject

@HiltViewModel
class FavoritesVideoViewModel @Inject constructor(
    private val dialogManager: DialogManager,
    private val getFavoriteVideosUseCase: GetFavoriteVideosUseCase,
    private val deleteFavoriteVideoUseCase: DeleteFavoriteVideoUseCase,
    private val clearAllFavoriteVideosUseCase: ClearFavoriteVideosUseCase,
) : BaseViewModel() {

    private val _favoriteVideos = MutableStateFlow<List<Video>>(emptyList())
    val favoriteVideos = _favoriteVideos.asStateFlow()

    private val _needShowDeleteButton = MutableStateFlow(false)
    val needShowDeleteButton = _needShowDeleteButton.asStateFlow()

    init {
        getFavoriteImages()
    }

    fun deleteFromFavoritesVideo(video: Video) {
        viewModelScope.launch {
            deleteFavoriteVideoUseCase(video)
        }
    }
    // TODO Don't hard code values
    fun clearAllVideos() {
        dialogManager.show(
            titleResId = "Delete all videos",
            messageResId = "Are you sure you want to delete all saved videos?",
            positiveAction = {
                viewModelScope.launch {
                    clearAllFavoriteVideosUseCase()
                }
            }
        )
    }

    private fun getFavoriteImages() {
        viewModelScope.launch {
            getFavoriteVideosUseCase()
                .collect {
                    _favoriteVideos.value = it
                    _needShowDeleteButton.value = it.isNotEmpty()
                }
        }
    }
}