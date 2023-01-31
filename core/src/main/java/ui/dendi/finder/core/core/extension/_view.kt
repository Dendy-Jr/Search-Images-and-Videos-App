package ui.dendi.finder.core.core.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.request.CachePolicy
import com.google.android.material.color.MaterialColors
import ui.dendi.finder.core.R

fun View.isVisible(condition: () -> Boolean) {
    isVisible = condition.invoke()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.loadImage(url: String) {
    val circularProgressDrawable = CircularProgressDrawable(context)
    circularProgressDrawable.strokeWidth = 5f
    circularProgressDrawable.centerRadius = 30f
    circularProgressDrawable.setColorSchemeColors(
        MaterialColors.getColor(
            this,
            R.attr.progressBarColor
        )
    )
    circularProgressDrawable.start()

    load(url) {
        placeholder(circularProgressDrawable)
        error(R.drawable.ic_image_load_error)
        diskCachePolicy(CachePolicy.ENABLED)
    }
}