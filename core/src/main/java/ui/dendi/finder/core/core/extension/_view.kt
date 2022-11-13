package ui.dendi.finder.core.core.extension

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load
import coil.request.CachePolicy
import com.google.android.material.color.MaterialColors
import com.google.android.material.snackbar.Snackbar
import ui.dendi.finder.core.R

fun View.isVisible(condition: () -> Boolean) {
    isVisible = condition.invoke()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.customSnackbar(
    @StringRes resId: Int,
    @DrawableRes drawableId: Int = R.drawable.bg_image,
) {
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
        setBackgroundTint(ContextCompat.getColor(this.context, R.color.red_700))
        setTextColor(ContextCompat.getColor(this.context, R.color.amber_300))
        view.layoutParams = params
        view.background = ContextCompat.getDrawable(this.context, drawableId)
    }
    snackbar.show()
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

fun RecyclerView.scrollToTop(button: View) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val manager = (recyclerView.layoutManager as? LinearLayoutManager) ?: return
            val needScroll = manager.findFirstVisibleItemPosition() > 0
            button.isVisible = needScroll

            button.setOnClickListener {
                if (needScroll) {
                    smoothScrollToPosition(0)
                }
            }
        }
    })
}