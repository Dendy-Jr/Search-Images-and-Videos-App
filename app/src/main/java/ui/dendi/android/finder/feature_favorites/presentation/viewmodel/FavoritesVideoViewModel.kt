package ui.dendi.android.finder.feature_favorites.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.android.finder.core.base.BaseViewModel
import ui.dendi.android.finder.feature_favorites.domain.usecase.ClearVideosUseCase
import ui.dendi.android.finder.feature_favorites.domain.usecase.DeleteVideoUseCase
import ui.dendi.android.finder.feature_videos.domain.Video
import ui.dendi.android.finder.feature_videos.domain.usecase.GetVideosUseCase
import javax.inject.Inject

@HiltViewModel
class FavoritesVideoViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
    private val deleteVideoUseCase: DeleteVideoUseCase,
    private val clearVideosUseCase: ClearVideosUseCase,
) : BaseViewModel() {

    val favoritesVideo = getVideosUseCase()

    fun deleteFromFavoritesVideo(video: Video) {
        viewModelScope.launch {
            deleteVideoUseCase(video)
        }
    }

    fun deleteAllFavoritesVideos() {
        viewModelScope.launch {
            clearVideosUseCase()
        }
    }
}