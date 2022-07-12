package com.dendi.android.search_images_and_videos_app.feature_images.presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.managers.DialogManager
import com.dendi.android.search_images_and_videos_app.core.ResourceProvider
import com.dendi.android.search_images_and_videos_app.core.base.BaseViewModel
import com.dendi.android.search_images_and_videos_app.feature_images.domain.usecase.DownloadFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val dialogManager: DialogManager,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val resourceProvider: ResourceProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val args = ImageDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

    fun downloadImage() {
        viewModelScope.launch {
            downloadFileUseCase(
                url = args.image.largeImageURL,
                fileName = args.image.tags.replace(", ", "-") + ".jpg",
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