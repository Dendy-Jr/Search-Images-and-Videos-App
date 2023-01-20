package ui.dendi.finder.videos_presentation.videos

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kohii.v1.core.Common
import kohii.v1.exoplayer.Kohii
import ui.dendi.finder.core.core.multichoice.VideoListItem
import ui.dendi.finder.videos_presentation.R
import ui.dendi.finder.videos_presentation.databinding.VideoItemBinding

class VideosPagingAdapter(
    private val kohii: Kohii,
    private val listener: VideoAdapterListener,
) : PagingDataAdapter<VideoListItem, VideosPagingAdapter.VideoViewHolder>(ItemCallback),
    View.OnClickListener, View.OnLongClickListener {

    @SuppressLint("NotifyDataSetChanged")
    fun getVideoListItem(position: Int): VideoListItem? {
        notifyDataSetChanged()
        return getItem(position)
    }

    override fun onClick(v: View) {
        val video = v.tag as VideoListItem
        when (v.id) {
            R.id.checkbox -> listener.onVideoToggle(video)
            else -> listener.onVideoChose(video)
        }
    }

    override fun onLongClick(v: View): Boolean {
        val video = v.tag as VideoListItem
        listener.onVideoToggle(video)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = VideoItemBinding.inflate(inflate, parent, false)

        binding.apply {
            root.setOnClickListener(this@VideosPagingAdapter)
            root.setOnLongClickListener(this@VideosPagingAdapter)
            checkbox.setOnClickListener(this@VideosPagingAdapter)
        }
        return VideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val videoItem = getItem(position) ?: return
        kohii.setUp(videoItem.videos.tiny.url) {
            tag = "${videoItem.videos.tiny.url}+${position}"
            repeatMode = Common.REPEAT_MODE_ONE
            preload = true
        }.bind(holder.playerContainer)
        holder.bind(getItem(position)!!)
    }

    inner class VideoViewHolder(
        private val binding: VideoItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val playerContainer = binding.playerContainer

        fun bind(item: VideoListItem) = with(binding) {
            root.tag = item
            checkbox.tag = item

            checkbox.isChecked = item.isChecked

            // TODO move `share` into details screen
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
        fun onVideoToggle(video: VideoListItem)
    }
}