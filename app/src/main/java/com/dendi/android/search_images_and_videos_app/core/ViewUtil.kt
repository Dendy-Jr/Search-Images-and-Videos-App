package com.dendi.android.search_images_and_videos_app.core

import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.dendi.android.search_images_and_videos_app.R
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar

/**
 * @author Dendy-Jr on 13.12.2021
 * olehvynnytskyi@gmail.com
 */

fun logi(message: String) {
    Log.i("Download Worker - ", message)
}
//@StringRes
 fun showToast( messageRes: String, context: Context) {
    Toast.makeText(context, messageRes, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    view: View = requireView()
) {
    Snackbar.make(view, message, duration).show()
}

inline fun <T : View> T.showIfOrInvisible(condition: (T) -> Boolean) {
    if (condition(this)) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun View.requestNewSize(
    width: Int? = null, height: Int? = null
) {
    if (width != null) {
        layoutParams.width = width
    }
    if (height != null) {
        layoutParams.height = height
    }
    layoutParams = layoutParams
}

fun View.visibleIf(boolean: Boolean) {
    visibility = if (boolean) View.VISIBLE else View.GONE
}

fun ImageView.loadImageOriginal(imageUri: String, loadListener: GlideLoadStatus? = null) {
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
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                loadListener?.imageLoadStatus(false)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                loadListener?.imageLoadStatus(true)
                return false
            }
        })
        .into(this)
}

fun Context.showKeyboard(view: View) {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
    imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            // do nothing
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // do nothing
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }
    })
}


interface GlideLoadStatus {
    fun imageLoadStatus(success: Boolean)
}