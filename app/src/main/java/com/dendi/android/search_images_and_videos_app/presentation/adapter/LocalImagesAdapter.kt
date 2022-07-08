package com.dendi.android.search_images_and_videos_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dendi.android.search_images_and_videos_app.core.extension.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.databinding.ImageSearchItemBinding
import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image

class LocalImagesAdapter :
    ListAdapter<Image, LocalImagesAdapter.LocalImagesViewHolder>(ItemCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageSearchItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).let(::LocalImagesViewHolder)

    override fun onBindViewHolder(holder: LocalImagesViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    inner class LocalImagesViewHolder(
        private val binding: ImageSearchItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Image) {
            binding.imageView.loadImageOriginal(item.largeImageURL)
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image) =
            oldItem == newItem
    }
}