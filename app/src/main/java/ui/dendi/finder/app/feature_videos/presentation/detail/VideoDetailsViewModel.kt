package ui.dendi.finder.app.feature_videos.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.finder.app.R
import ui.dendi.finder.app.core.ResourceProvider
import ui.dendi.finder.app.core.base.BaseViewModel
import ui.dendi.finder.app.core.managers.DialogManager
import ui.dendi.finder.app.feature_images.domain.usecase.DownloadFileUseCase
import javax.inject.Inject

@HiltViewModel
class VideoDetailsViewModel @Inject constructor(
    private val downloadFileUseCase: DownloadFileUseCase,
    private val dialogManager: DialogManager,
    private val resourceProvider: ResourceProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val args = VideoDetailsFragmentArgs.fromSavedStateHandle(savedStateHandle)

    fun downloadVideo() {
        viewModelScope.launch {
            downloadFileUseCase(
                url = args.video.videos.large.url,
                fileName = args.video.tags.replace(", ", "-") + ".mp4",
            )
                .onSuccess {
                    dialogManager.show(
                        titleResId = resourceProvider.getString(R.string.download_file),
                        messageResId = resourceProvider.getString(R.string.image_downloaded_successfully),
                        cancelDialog = true,
                    )
                    Log.d("DOWNLOAD_FILE", "Successfully")
                }
        }
    }
}