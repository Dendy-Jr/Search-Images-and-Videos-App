package com.dendi.android.search_images_and_videos_app.core.extension

import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun Fragment.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    view: View = requireView(),
) {
    Snackbar.make(view, message, duration).show()
}