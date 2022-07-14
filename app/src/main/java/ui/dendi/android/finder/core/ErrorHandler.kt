package ui.dendi.android.finder.core

import ui.dendi.android.finder.R
import ui.dendi.android.finder.core.managers.DialogManager
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