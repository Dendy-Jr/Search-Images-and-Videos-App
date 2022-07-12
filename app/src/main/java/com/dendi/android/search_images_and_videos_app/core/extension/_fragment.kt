package com.dendi.android.search_images_and_videos_app.core.extension

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun Fragment.showSnackbar(
    message: String,
    duration: Int = Snackbar.LENGTH_SHORT,
    view: View = requireView(),
) {
    Snackbar.make(view, message, duration).show()
}

context(Fragment)
fun <T> Flow<T>.collectWithLifecycle(block: (T) -> Unit) {
    viewLifecycleOwnerLiveData.observe(this@Fragment) {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    collect { block(it) }
                }
            }
        }
    }
}