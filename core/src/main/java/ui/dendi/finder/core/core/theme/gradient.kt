package ui.dendi.finder.core.core.theme

import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ui.dendi.finder.core.R
import ui.dendi.finder.core.core.extension.getColorAttr

context(View)
fun Paint.applyGradient() {
    val colorGradientStart = context.getColorAttr(R.attr.colorGradientStart)
    val colorGradientEnd = context.getColorAttr(R.attr.colorGradientEnd)

    addOnLayoutChangeListener { _, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
        if (left != oldLeft || top != oldTop || right != oldRight || bottom != oldBottom) {
            shader = LinearGradient(
                0f,
                0f,
                width.toFloat(),
                height.toFloat(),
                colorGradientStart,
                colorGradientEnd,
                Shader.TileMode.CLAMP
            )
        }
    }
}

fun TextView.applyTextColorGradient() {
    with(layoutParams) {
        if (width != ViewGroup.LayoutParams.WRAP_CONTENT || height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            throw IllegalStateException("LayoutParams must be wrap_content")
        }
    }

    setTextColor(context.getColorAttr(R.attr.colorGradientStart))
    paint.applyGradient()
}