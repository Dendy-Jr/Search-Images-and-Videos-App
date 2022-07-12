package com.dendi.android.search_images_and_videos_app.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.databinding.ProgressCircularItemBinding

class DefaultLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): Holder {
        val binding = ProgressCircularItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return Holder(binding, null, retry)
    }

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class Holder(
        private val binding: ProgressCircularItemBinding,
        private val swipeRefreshLayout: SwipeRefreshLayout?,
        private val retry: () -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnRetry.setOnClickListener {
                retry.invoke()
            }
        }

        fun bind(loadState: LoadState) = with(binding) {
            btnRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = loadState is LoadState.Error
            tvError.text = binding.root.context.getString(R.string.unknown_error_occurred)

            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.isRefreshing = loadState is LoadState.Loading
                progressBar.isVisible = false
            } else {
                progressBar.isVisible = loadState is LoadState.Loading
            }
        }
    }
}