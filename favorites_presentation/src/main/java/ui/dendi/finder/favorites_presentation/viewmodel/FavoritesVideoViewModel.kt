package ui.dendi.finder.favorites_presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.ResourceProvider
import ui.dendi.finder.favorites_domain.videos.usecase.ClearFavoriteVideosUseCase
import ui.dendi.finder.favorites_domain.videos.usecase.DeleteFavoriteVideoUseCase
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.managers.DialogManager
import ui.dendi.finder.core.core.models.Video
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.videos_domain.usecase.GetFavoriteVideosUseCase
import javax.inject.Inject

@HiltViewModel
class FavoritesVideoViewModel @Inject constructor(
    private val clearAllFavoriteVideosUseCase: ClearFavoriteVideosUseCase,
    private val deleteFavoriteVideoUseCase: DeleteFavoriteVideoUseCase,
    private val dialogManager: DialogManager,
    private val getFavoriteVideosUseCase: GetFavoriteVideosUseCase,
    private val resourceProvider: ResourceProvider,
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

    fun clearAllVideos() {
        dialogManager.show(
            titleResId = resourceProvider.getString(R.string.delete_all_videos_title),
            messageResId = resourceProvider.getString(R.string.delete_all_saved_videos_question),
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