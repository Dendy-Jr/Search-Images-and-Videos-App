package com.dendi.android.search_images_and_videos_app.presentation.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import com.dendi.android.search_images_and_videos_app.databinding.VideoItemBinding
import kohii.v1.core.Common
import kohii.v1.exoplayer.Kohii
import kotlinx.serialization.ExperimentalSerializationApi

/**
 * @author Dendy-Jr on 11.12.2021
 * olehvynnytskyi@gmail.com
 */
@ExperimentalSerializationApi
class VideoPagingAdapter(
    private val kohii: Kohii,
    private val toVideo: OnClickListener<VideoCache>,
    private val addToFavorite: OnClickListener<VideoCache>,
    private val shareVideo: OnClickListener<VideoCache>,
) : PagingDataAdapter<VideoCache, VideoPagingAdapter.VideoHolder>(Companion) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoHolder {
        val binding = VideoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VideoHolder(binding, toVideo, addToFavorite, shareVideo)
    }

    override fun onBindViewHolder(holder: VideoHolder, position: Int) {
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

    @ExperimentalSerializationApi
    inner class VideoHolder(
        private val binding: VideoItemBinding,
        private val toVideo: OnClickListener<VideoCache>,
        private val addToFavorite: OnClickListener<VideoCache>,
        private val shareVideo: OnClickListener<VideoCache>,
    ) : RecyclerView.ViewHolder(binding.root) {

        lateinit var video: VideoCache
        val swipe = binding.swipeRevealLayout
        val playerContainer = binding.playerContainer

        fun bind(mVideos: VideoCache) {
            this.video = mVideos
        }

        init {
            binding.playerContainer.setOnClickListener {
                toVideo.click(video)
            }

            binding.addToFavorite.setOnClickListener {
                video.isFavorite = true
                addToFavorite.click(video)
                swipe.close(false)
            }

            binding.shareBnt.setOnClickListener {
                shareVideo.click(video)
                swipe.close(false)
            }
        }
    }

    companion object : DiffUtil.ItemCallback<VideoCache>() {
        override fun areItemsTheSame(oldItem: VideoCache, newItem: VideoCache) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: VideoCache, newItem: VideoCache) =
            oldItem == newItem
    }
}

