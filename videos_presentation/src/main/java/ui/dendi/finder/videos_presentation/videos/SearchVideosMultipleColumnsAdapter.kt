package ui.dendi.finder.videos_presentation.videos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kohii.v1.core.Common
import kohii.v1.core.Playback
import kohii.v1.exoplayer.Kohii
import ui.dendi.finder.core.core.multichoice.VideoListItem
import ui.dendi.finder.videos_presentation.R
import ui.dendi.finder.videos_presentation.databinding.VideoItemMultipleColumnsBinding

class SearchVideosMultipleColumnsAdapter(
    private val kohii: Kohii,
    private val listener: VideoAdapterListener,
) : PagingDataAdapter<VideoListItem, SearchVideosMultipleColumnsAdapter.VideoViewHolder>(
    ItemCallback
), View.OnClickListener, View.OnLongClickListener {

    override fun onClick(v: View) {
        val video = v.tag as VideoListItem
        if (v.id == R.id.container) listener.onVideoChose(video)
    }

    override fun onLongClick(v: View): Boolean {
        val video = v.tag as VideoListItem
        listener.addToFavorite(video)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = VideoItemMultipleColumnsBinding.inflate(inflate, parent, false)

        binding.apply {
            container.setOnClickListener(this@SearchVideosMultipleColumnsAdapter)
            container.setOnLongClickListener(this@SearchVideosMultipleColumnsAdapter)
        }
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = getItem(position) ?: return
        kohii.setUp(videoItem.videos.tiny.url) {
            tag = "${videoItem.videos.tiny.url}+${position}"
            repeatMode = Common.REPEAT_MODE_ONE
            preload = true
            artworkHintListener = holder
        }.bind(holder.playerContainer)
        holder.bind(getItem(position)!!)
    }

    inner class VideoViewHolder(
        private val binding: VideoItemMultipleColumnsBinding,
    ) : RecyclerView.ViewHolder(binding.root), Playback.ArtworkHintListener {

        val playerContainer = binding.playerContainer

        fun bind(item: VideoListItem) = with(binding) {
            container.tag = item
            // TODO move `share` into details screen
        }

        override fun onArtworkHint(
            playback: Playback,
            shouldShow: Boolean,
            position: Long,
            state: Int
        ) {
            binding.ivThumbnail.isVisible = shouldShow
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<VideoListItem>() {
        override fun areItemsTheSame(oldItem: VideoListItem, newItem: VideoListItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: VideoListItem, newItem: VideoListItem) =
            oldItem == newItem
    }

    interface VideoAdapterListener {
        fun onVideoChose(video: VideoListItem)
        fun addToFavorite(video: VideoListItem)
    }
}