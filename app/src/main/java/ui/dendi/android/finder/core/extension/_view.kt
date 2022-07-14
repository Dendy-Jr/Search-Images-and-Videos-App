package ui.dendi.android.finder.core.extension

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.color.MaterialColors
import ui.dendi.android.finder.R

fun View.isVisible(condition: () -> Boolean) {
    isVisible = condition.invoke()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun ImageView.loadImageOriginal(imageUri: String) {
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

    Glide.with(context)
        .applyDefaultRequestOptions(
            RequestOptions()
                .dontTransform()
                .dontAnimate()
                .placeholder(circularProgressDrawable)
                .error(R.drawable.ic_image_load_error)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .override(Target.SIZE_ORIGINAL)
        )
        .load(imageUri)
        .into(this)
}