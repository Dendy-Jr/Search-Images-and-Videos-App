package com.dendi.android.search_images_and_videos_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.databinding.ProgressCircularItemBinding

class DefaultLoadStateAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<DefaultLoadStateAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ProgressCircularItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).let(::Holder)

    override fun onBindViewHolder(holder: Holder, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class Holder(
        private val binding: ProgressCircularItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) = with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            btnRetry.isVisible = loadState is LoadState.Error
            tvError.isVisible = loadState is LoadState.Error

            if (loadState is LoadState.Error) {
                tvError.text = loadState.error.localizedMessage
                    ?: binding.root.context.getString(R.string.unknown_error_occurred)
            }
            btnRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}