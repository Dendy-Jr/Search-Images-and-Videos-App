package ui.dendi.finder.core.core.managers

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.annotation.DrawableRes
import ui.dendi.finder.core.R
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
        @DrawableRes iconResId: Int? = null,
        titleResId: String,
        messageResId: String,
        cancelDialog: Boolean = false,
        positiveAction: (() -> Unit)? = null,
        negativeAction: (() -> Unit)? = null,
        setNegativeButtonText: String = resourceProvider.getString(R.string.no),
    ) {

        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> positiveAction?.invoke()
                DialogInterface.BUTTON_NEGATIVE -> negativeAction?.invoke()
            }
        }

        val activity = activityEngine.currentActivity ?: return

        val dialog = AlertDialog.Builder(activity).apply {
            setCancelable(cancelDialog)
            if (iconResId != null) {
                setIcon(iconResId)
            }
            setTitle(titleResId)
            setMessage(messageResId)
            setPositiveButton(context.getString(R.string.yes), listener)
            setNegativeButton(setNegativeButtonText, listener)
            setOnDismissListener { it.dismiss() }
        }
            .create()
        dialog.show()
    }
}