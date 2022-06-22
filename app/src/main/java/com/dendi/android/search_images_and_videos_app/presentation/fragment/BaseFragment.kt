package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dendi.android.search_images_and_videos_app.core.extension.hideKeyboard
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseFragment(fragmentImages: Int) : Fragment(fragmentImages) {

    protected val navController by lazy { findNavController() }
    protected abstract fun setRecyclerView(): RecyclerView?
    protected var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = setRecyclerView()
        recyclerView?.run {
            layoutManager = LinearLayoutManager(requireContext())
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                        recyclerView.hideKeyboard()
                    }
                }
            })
            setHasFixedSize(true)
            itemAnimator = null
            addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
        }
    }

    protected fun shareItem(userName: String, url: String) {
        val sendIntent = Intent(Intent.ACTION_SEND).apply {
            putExtra(
                Intent.EXTRA_TEXT,
                "${userName}\n${url}"
            )
            type = "text/plain"
        }
        val sharedIntent = Intent.createChooser(sendIntent, userName)
        startActivity(sharedIntent)
    }

    protected fun setAdapter(adapter: RecyclerView.Adapter<*>) {
        recyclerView?.adapter = adapter
    }

    protected fun <T> Fragment.collectWithLifecycle(
        flow: Flow<T>,
        collect: suspend (T) -> Unit,
    ) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                flow.collectLatest(collect)
            }
        }
    }
}