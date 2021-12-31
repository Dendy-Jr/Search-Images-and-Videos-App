package com.dendi.android.search_images_and_videos_app.presentation.video

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.visibleIf
import com.dendi.android.search_images_and_videos_app.databinding.ProgressCircularItemBinding

/**
 * @author Dendy-Jr on 13.12.2021
 * olehvynnytskyi@gmail.com
 */
class LoadAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<LoadAdapter.ProgressHolderCircular>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState,
    ): ProgressHolderCircular {
        return ProgressHolderCircular(
            ProgressCircularItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProgressHolderCircular, loadState: LoadState) {
      holder.bind(loadState)
    }

    inner class ProgressHolderCircular(private val view: ProgressCircularItemBinding) :
        RecyclerView.ViewHolder(view.root) {

        init {
            view.run {
                buttonRetry.setOnClickListener {
                    retry()
                }
            }
        }

        fun bind(loadState: LoadState) {
            view.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                buttonRetry.isVisible = loadState is LoadState.Error
                textViewError.isVisible = loadState is LoadState.Error

                if (loadState is LoadState.Error) {
                    textViewError.text = loadState.error.localizedMessage
                        ?: view.root.context.getString(R.string.unknown_error_occurred)
                }
            }
        }
    }
}