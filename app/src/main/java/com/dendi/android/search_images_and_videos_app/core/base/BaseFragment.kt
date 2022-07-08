package com.dendi.android.search_images_and_videos_app.core.base

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.dendi.android.search_images_and_videos_app.core.extension.hideKeyboard

abstract class BaseFragment<VM : BaseViewModel>(
    @LayoutRes contentLayoutId: Int
) : Fragment(contentLayoutId), ViewModelOwner<VM> {

    abstract override val viewModel: VM

    protected abstract fun setRecyclerView(): RecyclerView?
    protected var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = setRecyclerView()
        recyclerView?.run {
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        recyclerView.hideKeyboard()
                    }
                }
            })
        }
    }

    protected fun shareItem(userName: String, url: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(
                Intent.EXTRA_TEXT,
                "${userName}\n${url}",
            )
            type = "text/plain"
        }
        val sharedIntent = Intent.createChooser(sendIntent, userName)
        startActivity(sharedIntent)
    }

    protected fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView?.adapter = adapter
    }
}