package ui.dendi.finder.videos_presentation.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.base.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(
    logger: Logger,
    savedStateHandle: SavedStateHandle,
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
}

enum class VideoQuality {
    LARGE, MEDIUM, SMALL, TINY;
}