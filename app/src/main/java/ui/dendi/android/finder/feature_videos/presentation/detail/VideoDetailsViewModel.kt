package ui.dendi.android.finder.feature_videos.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.android.finder.R
import ui.dendi.android.finder.core.ResourceProvider
import ui.dendi.android.finder.core.base.BaseViewModel
import ui.dendi.android.finder.core.managers.DialogManager
import ui.dendi.android.finder.feature_images.domain.usecase.DownloadFileUseCase
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