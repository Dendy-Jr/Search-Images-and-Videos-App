package com.dendi.android.search_images_and_videos_app.presentation.image

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.data.image.cloud.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

/**
 * @author Dendy-Jr on 30.12.2021
 * olehvynnytskyi@gmail.com
 */
class ImageDetailViewModel(
    state: SavedStateHandle
) : ViewModel() {

    init {
        EventBus.getDefault().register(this)
    }

    private val imageId = state.get<ImageDto>("image")?.id
    val userImageViewVisibility = MutableLiveData(false)
    val imageDownloadInProgress =
        MutableLiveData(DownloadWorker.downloadingFilesIdSet?.contains(imageId) == true)
    val imageDownloadPercent = MutableLiveData<Int>()

    @Subscribe
    fun fileDownloadStarted(event: FileDownloadStarted) {
        if (event.fileId == imageId) {
            imageDownloadInProgress.value = true
        }
    }

    @Subscribe
    fun fileDownloadProgressEvent(event: FileDownloadProgressEvent) {
        if (event.fileId == imageId) {
            imageDownloadPercent.value = event.progress
        }
    }

    @Subscribe
    fun fileDownloadEnded(event: FileDownloadEnded) {
        if (event.fileId == imageId) {
            imageDownloadInProgress.value = false
        }
    }

    override fun onCleared() {
        EventBus.getDefault().unregister(this)
    }
}