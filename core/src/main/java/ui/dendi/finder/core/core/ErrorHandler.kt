package ui.dendi.finder.core.core

import ui.dendi.finder.core.R
import ui.dendi.finder.core.core.managers.DialogManager
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
        //TODO Maybe need to use it in the future
    }
}