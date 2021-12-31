package com.dendi.android.search_images_and_videos_app.presentation.favorite

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.core.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.databinding.FavoriteImageItemBinding

/**
 * @author Dendy-Jr on 27.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesImageAdapter(
    private val deleteFromFavorite: OnClickListener<ImageEntity>
) : ListAdapter<ImageEntity, FavoritesImageAdapter.ListViewHolder>(Companion) {

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
        private val deleteFromFavorite: OnClickListener<ImageEntity>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var imageEntity: ImageEntity
        val swipe = binding.swipeRevealLayout

        fun bind(image: ImageEntity) {
            imageEntity = image
            binding.imageView.loadImageOriginal(image.largeImageURL)
        }

        init {
            binding.deleteFromFavorite.setOnClickListener {
                imageEntity.isFavorite = false
                deleteFromFavorite.click(imageEntity)
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