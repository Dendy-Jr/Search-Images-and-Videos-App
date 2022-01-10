package com.dendi.android.search_images_and_videos_app.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.dendi.android.search_images_and_videos_app.core.GlideLoadStatus
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.core.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.databinding.FavoriteImageItemBinding
import com.dendi.android.search_images_and_videos_app.domain.image.Image

/**
 * @author Dendy-Jr on 27.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesImageAdapter(
    private val deleteFromFavorite: OnClickListener<Image>
) : ListAdapter<Image, FavoritesImageAdapter.ListViewHolder>(Companion) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            FavoriteImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            deleteFromFavorite
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(getItem(position))
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipe, getItem(position).id.toString())
        viewBinderHelper.closeLayout(getItem(position).id.toString())
    }

    inner class ListViewHolder(
        private val binding: FavoriteImageItemBinding,
        private val deleteFromFavorite: OnClickListener<Image>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var imageEntity: Image
        private var imageLoadAnyStatus = false
        private val glideLoadStatus = object : GlideLoadStatus {
            override fun imageLoadStatus(success: Boolean) {
                imageLoadAnyStatus = success
            }
        }
        val swipe = binding.swipeRevealLayout

        fun bind(image: Image) {
            imageEntity = image
            binding.imageView.loadImageOriginal(image.largeImageURL, glideLoadStatus)
        }

        init {
            binding.deleteFromFavorite.setOnClickListener {
                imageEntity.isFavorite = false
                deleteFromFavorite.click(imageEntity)
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