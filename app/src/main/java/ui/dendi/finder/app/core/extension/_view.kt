package ui.dendi.finder.app.core.extension

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import ui.dendi.finder.app.R

fun View.isVisible(condition: () -> Boolean) {
    isVisible = condition.invoke()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.customSnackbar(@StringRes resId: Int) {
    val snackbar = Snackbar.make(this, resId, Snackbar.LENGTH_SHORT)
    val params = snackbar.view.layoutParams as (FrameLayout.LayoutParams)
    params.setMargins(16, 0, 16, 200)
    params.width = FrameLayout.LayoutParams.WRAP_CONTENT
    params.gravity = Gravity.BOTTOM or Gravity.CENTER

    val textView =
        snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.setCompoundDrawablesWithIntrinsicBounds(
        R.drawable.ic_like,
        0,
        0,
        0
    )
    textView.compoundDrawablePadding = 20
    snackbar.apply {
        setBackgroundTint(ContextCompat.getColor(this.context, R.color.white))
        setTextColor(ContextCompat.getColor(this.context, R.color.amber_300))
        view.layoutParams = params
        view.background = ContextCompat.getDrawable(this.context, R.drawable.bg_image)
    }
    snackbar.show()
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