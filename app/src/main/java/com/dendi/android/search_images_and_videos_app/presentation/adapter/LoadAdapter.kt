package com.dendi.android.search_images_and_videos_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.databinding.ProgressCircularItemBinding

class LoadAdapter(
    private val retry: () -> Unit,
) : LoadStateAdapter<LoadAdapter.ProgressHolderCircular>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        ProgressCircularItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).let(::ProgressHolderCircular)

    override fun onBindViewHolder(holder: ProgressHolderCircular, loadState: LoadState) {
        holder.bind(loadState)
    }

    inner class ProgressHolderCircular(
        private val binding: ProgressCircularItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) = with(binding) {
            progressBar.isVisible = loadState is LoadState.Loading
            buttonRetry.isVisible = loadState is LoadState.Error
            textViewError.isVisible = loadState is LoadState.Error

            if (loadState is LoadState.Error) {
                textViewError.text = loadState.error.localizedMessage
                    ?: binding.root.context.getString(R.string.unknown_error_occurred)
            }
            buttonRetry.setOnClickListener {
                retry.invoke()
            }
        }
    }
}