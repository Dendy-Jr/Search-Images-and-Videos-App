package ui.dendi.finder.core.core.extension

import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

fun ConstraintLayout.applyConstraint(block: ConstraintSet.() -> Unit) {
    ConstraintSet().apply {
        clone(this@applyConstraint)
        block(this)
    }.applyTo(this)
}

fun ConstraintSet.topToBottom(v1: View, v2: View, margin: Int = 0) {
    connect(v1.id, ConstraintSet.TOP, v2.id, ConstraintSet.BOTTOM, margin.dp)
}