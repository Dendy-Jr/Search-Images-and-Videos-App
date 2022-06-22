package com.dendi.android.search_images_and_videos_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.dendi.android.search_images_and_videos_app.core.extension.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.databinding.ImageItemBinding
import com.dendi.android.search_images_and_videos_app.domain.image.Image

class ImagePagingAdapter(
    private val toImage: (Image) -> Unit,
    private val addToFavorite: (Image) -> Unit,
    private val shareImage: (Image) -> Unit,
) : PagingDataAdapter<Image, ImagePagingAdapter.ImagePagingViewHolder>(ItemCallback) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).let(::ImagePagingViewHolder)


    override fun onBindViewHolder(holder: ImagePagingViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            viewBinderHelper.setOpenOnlyOne(true)
            viewBinderHelper.bind(holder.swipe, currentItem.id.toString())
            viewBinderHelper.closeLayout(currentItem.id.toString())
        }
    }

    inner class ImagePagingViewHolder(
        private val binding: ImageItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val swipe = binding.swipeRevealLayout

        fun bind(item: Image) = with(binding) {
            imageView.loadImageOriginal(item.largeImageURL)

            imageView.setOnClickListener {
                toImage.invoke(item)
            }

            ibAdd.setOnClickListener {
                item.isFavorite = true
                addToFavorite.invoke(item)
                swipe.close(false)
            }

            shareBnt.setOnClickListener {
                shareImage.invoke(item)
                swipe.close(false)
            }
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image) =
            oldItem == newItem
    }
}