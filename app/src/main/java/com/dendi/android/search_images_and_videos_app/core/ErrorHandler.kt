package com.dendi.android.search_images_and_videos_app.core

import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.managers.DialogManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandler @Inject constructor(
    private val dialogManager: DialogManager,
    private val resourceProvider: ResourceProvider,
) {

    fun onError(throwable: Throwable) {
        dialogManager.show(
            titleResId = resourceProvider.getString(R.string.error),
            messageResId = throwable.message.toString(),
            setNegativeButtonText = "Cancel",
        )
    }

    interface ErrorCallback {

        fun action(action: () -> Unit)
        //TODO
    }
}