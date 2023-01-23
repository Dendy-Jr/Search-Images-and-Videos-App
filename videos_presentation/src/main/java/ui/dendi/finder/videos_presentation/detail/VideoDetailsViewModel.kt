package ui.dendi.finder.videos_presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.videos_domain.usecase.SaveVideoToFavoritesUseCase
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(
    private val saveVideoToFavoritesUseCase: SaveVideoToFavoritesUseCase,
    savedStateHandle: SavedStateHandle,
    logger: Logger,
) : BaseViewModel(logger) {

    private val args = VideoDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val defaultQuality = args.video.videos.large.url

    val qualityFLow = MutableStateFlow(defaultQuality)

    fun setVideoQuality(quality: VideoQuality) {
        qualityFLow.value = when (quality) {
            VideoQuality.LARGE -> args.video.videos.large.url
            VideoQuality.MEDIUM -> args.video.videos.medium.url
            VideoQuality.SMALL -> args.video.videos.small.url
            VideoQuality.TINY -> args.video.videos.tiny.url
        }
    }

    fun saveVideoToFavorites() {
        viewModelScope.launch {
            val date = LocalDateTime.now()
            saveVideoToFavoritesUseCase(args.video.copy(date = date.toString()))
        }
    }
}

enum class VideoQuality {
    LARGE, MEDIUM, SMALL, TINY;
}