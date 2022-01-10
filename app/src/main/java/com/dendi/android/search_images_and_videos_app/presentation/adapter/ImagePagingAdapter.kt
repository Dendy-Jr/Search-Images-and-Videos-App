package com.dendi.android.search_images_and_videos_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.dendi.android.search_images_and_videos_app.core.GlideLoadStatus
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.core.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.databinding.ImageItemBinding
import com.dendi.android.search_images_and_videos_app.domain.image.Image

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
class ImagePagingAdapter(
    private val toImage: OnClickListener<Image>,
    private val addToFavorite: OnClickListener<Image>,
    private val shareImage: OnClickListener<Image>,
) : PagingDataAdapter<Image, ImagePagingAdapter.ImageViewHolder>(Companion) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding, toImage, addToFavorite, shareImage)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            viewBinderHelper.setOpenOnlyOne(true)
            viewBinderHelper.bind(holder.swipe, currentItem.id.toString())
            viewBinderHelper.closeLayout(currentItem.id.toString())
        }
    }

    inner class ImageViewHolder(
        private val binding: ImageItemBinding,
        private val toImage: OnClickListener<Image>,
        private val addToFavorite: OnClickListener<Image>,
        private val shareImage: OnClickListener<Image>
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var imageEntity: Image
        private var imageLoadAnyStatus = false
        private val glideLoadStatus = object : GlideLoadStatus {
            override fun imageLoadStatus(success: Boolean) {
                imageLoadAnyStatus = success
            }
        }
        val swipe = binding.swipeRevealLayout

        fun bind(image: Image) {
            this.imageEntity = image
            binding.imageView.loadImageOriginal(image.largeImageURL, glideLoadStatus)
        }

        init {
            binding.imageView.setOnClickListener {
                toImage.click(imageEntity)
            }

            binding.addToFavorite.setOnClickListener {
                imageEntity.isFavorite = true
                addToFavorite.click(imageEntity)
                swipe.close(false)
            }

            binding.shareBnt.setOnClickListener {
                shareImage.click(imageEntity)
                swipe.close(false)
            }
        }
    }

    companion object : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image) =
            oldItem == newItem
    }
}