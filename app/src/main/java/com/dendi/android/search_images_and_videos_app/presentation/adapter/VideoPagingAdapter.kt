package com.dendi.android.search_images_and_videos_app.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.dendi.android.search_images_and_videos_app.databinding.VideoItemBinding
import com.dendi.android.search_images_and_videos_app.domain.video.Video
import kohii.v1.core.Common
import kohii.v1.exoplayer.Kohii

class VideoPagingAdapter(
    private val kohii: Kohii,
    private val toVideo: (Video) -> Unit,
    private val addToFavorite: (Video) -> Unit,
    private val shareVideo: (Video) -> Unit,
) : PagingDataAdapter<Video, VideoPagingAdapter.VideoViewHolder>(ItemCallback) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VideoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).let(::VideoViewHolder)

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = getItem(position) ?: return
        kohii.setUp(videoItem.videos.large.url) {
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

    inner class VideoViewHolder(
        private val binding: VideoItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val swipe = binding.swipeRevealLayout
        val playerContainer = binding.playerContainer

        fun bind(item: Video) = with(binding) {
            playerContainer.setOnClickListener {
                toVideo.invoke(item)
            }

            ibAdd.setOnClickListener {
                item.isFavorite = true
                addToFavorite.invoke(item)
                swipe.close(false)
            }

            shareBnt.setOnClickListener {
                shareVideo.invoke(item)
                swipe.close(false)
            }
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Video, newItem: Video) =
            oldItem == newItem
    }
}

