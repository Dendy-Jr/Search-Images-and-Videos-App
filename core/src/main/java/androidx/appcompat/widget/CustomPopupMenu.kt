package androidx.appcompat.widget

import android.content.Context
import android.view.View

class CustomPopupMenu constructor(context: Context, anchor: View) : PopupMenu(context, anchor) {

    init {
        mPopup.setForceShowIcon(true)
    }
}