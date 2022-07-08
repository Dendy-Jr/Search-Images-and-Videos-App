package com.dendi.android.search_images_and_videos_app.feature_favorites.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.dendi.android.search_images_and_videos_app.core.base.BaseViewModel
import com.dendi.android.search_images_and_videos_app.feature_favorites.domain.usecase.ClearVideosUseCase
import com.dendi.android.search_images_and_videos_app.feature_favorites.domain.usecase.DeleteVideoUseCase
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.Video
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.usecase.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesVideoViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase,
    private val deleteVideoUseCase: DeleteVideoUseCase,
    private val clearVideosUseCase: ClearVideosUseCase,
) : BaseViewModel() {

    val favoritesVideo = getVideosUseCase.getFavoritesVideos()

    fun deleteFromFavoritesVideo(video: Video) {
        viewModelScope.launch {
            deleteVideoUseCase.deleteFavorite(video)
        }
    }

    fun deleteAllFavoritesVideos() {
        viewModelScope.launch {
            clearVideosUseCase.clearAllFavorites()
        }
    }
}