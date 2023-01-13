package ui.dendi.finder.core.core.managers

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import ui.dendi.finder.core.core.ActivityEngine
import ui.dendi.finder.core.core.ResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DialogManager @Inject constructor(
    private val activityEngine: ActivityEngine,
    private val resourceProvider: ResourceProvider,
) {

    fun show(
        @StringRes titleResId: Int,
        @StringRes bodyResId: Int,
        @StringRes positiveButtonResId: Int,
        @StringRes negativeButtonResId: Int,
        onPositiveButtonClick: () -> Unit,
        onNegativeButtonClick: () -> Unit,
        viewProvider: DialogViewProvider? = null,
    ): DialogShownCallback = show(
        title = resourceProvider.getString(titleResId),
        body = resourceProvider.getString(bodyResId),
        positiveButton = resourceProvider.getString(positiveButtonResId),
        negativeButton = resourceProvider.getString(negativeButtonResId),
        onPositiveButtonClick = onPositiveButtonClick,
        onNegativeButtonClick = onNegativeButtonClick,
        viewProvider = viewProvider,
    )

    fun show(
        title: CharSequence,
        body: CharSequence,
        positiveButton: CharSequence,
        negativeButton: CharSequence,
        onPositiveButtonClick: () -> Unit,
        onNegativeButtonClick: () -> Unit,
        viewProvider: DialogViewProvider? = null,
    ): DialogShownCallback {

        var onShownCallback = {}
        val callback = DialogShownCallback { listener -> onShownCallback = listener }

        val activity = activityEngine.currentActivity ?: return run {
            onShownCallback.invoke()
            callback
        }

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> onPositiveButtonClick()
                DialogInterface.BUTTON_NEGATIVE -> onNegativeButtonClick()
            }
        }

        val dialog = AlertDialog.Builder(activity)
            .setTitle(title)
            .setMessage(body)
            .applyView(viewProvider)
            .setPositiveButton(positiveButton, listener)
            .setNegativeButton(negativeButton, listener)
            .create()

        val observer = object : DefaultLifecycleObserver {
            override fun onPause(owner: LifecycleOwner) {
                dialog.cancel()
                activity.lifecycle.removeObserver(this)
            }
        }
        activity.lifecycle.addObserver(observer)
        dialog.show()
        return callback
    }
}

private fun AlertDialog.Builder.applyView(provider: DialogViewProvider?) = apply {
    when (provider) {
        is DialogViewProvider.Id -> setView(provider.resId)
        is DialogViewProvider.Instance -> setView(provider.view)
        else -> {}
    }
}

sealed class DialogViewProvider {
    data class Id(@LayoutRes val resId: Int) : DialogViewProvider()

    data class Instance(val view: Int) : DialogViewProvider()
}

fun interface DialogShownCallback {
    fun onShown(listener: () -> Unit)
}