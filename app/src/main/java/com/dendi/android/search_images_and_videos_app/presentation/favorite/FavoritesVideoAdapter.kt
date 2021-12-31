package com.dendi.android.search_images_and_videos_app.presentation.favorite

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoEntity
import com.dendi.android.search_images_and_videos_app.databinding.FavoriteVideoItemBinding
import kohii.v1.core.Common
import kohii.v1.exoplayer.Kohii

/**
 * @author Dendy-Jr on 27.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesVideoAdapter(
    private val kohii: Kohii,
    private val deleteFromFavorite: OnClickListener<VideoEntity>
) : ListAdapter<VideoEntity, FavoritesVideoAdapter.ListViewHolder>(Companion) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(
            FavoriteVideoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            deleteFromFavorite
        )
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val videoItem = getItem(position) ?: return
        kohii.setUp(videoItem.videos.tiny.url) {
            tag = "${videoItem.videos.tiny.url}+${position}"
            Log.d("kohii", "${videoItem.videos.tiny.url}+${position}")
            repeatMode = Common.REPEAT_MODE_ONE
            preload = true
        }.bind(holder.playerContainer)
        holder.bind(getItem(position)!!)
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipe, videoItem.id.toString())
        viewBinderHelper.closeLayout(videoItem.id.toString())
    }

    inner class ListViewHolder(
        private val binding: FavoriteVideoItemBinding,
        private val deleteFromFavorite: OnClickListener<VideoEntity>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var videoEntity: VideoEntity
        val swipe = binding.swipeRevealLayout
        val playerContainer = binding.playerContainer

        fun bind(image: VideoEntity) {
            videoEntity = image
        }

        init {
            binding.deleteFromFavorite.setOnClickListener {
                videoEntity.isFavorite = false
                deleteFromFavorite.click(videoEntity)
                swipe.close(false)
            }
        }
    }

    companion object : DiffUtil.ItemCallback<VideoEntity>() {
        override fun areItemsTheSame(oldItem: VideoEntity, newItem: VideoEntity) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: VideoEntity, newItem: VideoEntity) =
            oldItem == newItem
    }
}