package ui.dendi.finder.images_presentation.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.ResourceProvider
import ui.dendi.finder.core.core.base.BaseViewModel
import ui.dendi.finder.core.core.managers.DialogManager
import ui.dendi.finder.images_domain.usecase.DownloadFileUseCase
import ui.dendi.finder.images_presentation.R
import javax.inject.Inject

@HiltViewModel
class ImageDetailViewModel @Inject constructor(
    private val dialogManager: DialogManager,
    private val downloadFileUseCase: DownloadFileUseCase,
    private val resourceProvider: ResourceProvider,
    savedStateHandle: SavedStateHandle,
) : BaseViewModel() {

    private val args = ImageDetailFragmentArgs.fromSavedStateHandle(savedStateHandle)

//    fun downloadImage() {
//        viewModelScope.launch {
//            downloadFileUseCase(
//                url = args.image.largeImageURL,
//                fileName = args.image.tags.replace(", ", "-") + ".jpg",
//            )
//                .onSuccess {
//                    dialogManager.show(
//                        titleResId = resourceProvider.getString(R.string.download_file),
//                        messageResId = resourceProvider.getString(R.string.image_downloaded_successfully),
//                        cancelDialog = true,
//                    )
//                    Log.d("DOWNLOAD_FILE", "Successfully")
//                }
//        }
//    }
}