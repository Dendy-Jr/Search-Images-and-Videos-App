package com.dendi.android.search_images_and_videos_app.presentation.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.core.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.databinding.ImageItemBinding

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
class ImagePagingAdapter(
    private val toImage: OnClickListener<ImageEntity>,
    private val addToFavorite: OnClickListener<ImageEntity>,
    private val shareImage: OnClickListener<ImageEntity>,
) : PagingDataAdapter<ImageEntity, ImagePagingAdapter.ImageViewHolder>(Companion) {

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
        private val toImage: OnClickListener<ImageEntity>,
        private val addToFavorite: OnClickListener<ImageEntity>,
        private val shareImage: OnClickListener<ImageEntity>
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var imageEntity: ImageEntity
        val swipe = binding.swipeRevealLayout

        fun bind(image: ImageEntity) {
            this.imageEntity = image
            binding.imageView.loadImageOriginal(image.largeImageURL)
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

    companion object : DiffUtil.ItemCallback<ImageEntity>() {
        override fun areItemsTheSame(oldItem: ImageEntity, newItem: ImageEntity) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ImageEntity, newItem: ImageEntity) =
            oldItem == newItem
    }
}