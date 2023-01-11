package ui.dendi.finder.favorites_presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kohii.v1.core.Common
import kohii.v1.exoplayer.Kohii
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.LoggerImpl
import ui.dendi.finder.core.core.extension.dateConverter
import ui.dendi.finder.core.core.multichoice.VideoListItem
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.favorites_presentation.databinding.FavoriteVideoItemBinding

class FavoritesVideoAdapter(
    private val kohii: Kohii,
    private val listener: VideoAdapterListener,
) : ListAdapter<VideoListItem, FavoritesVideoAdapter.FavoritesVideoViewHolder>(ItemCallback),
    Logger by LoggerImpl(), View.OnClickListener, View.OnLongClickListener {

    override fun onClick(v: View) {
        val video = v.tag as VideoListItem
        when (v.id) {
            R.id.deleteVideoView -> listener.onVideoDelete(video)
            R.id.checkbox -> listener.onVideoToggle(video)
            else -> listener.onVideoChosen(video)
        }
    }

    override fun onLongClick(v: View): Boolean {
        val video = v.tag as VideoListItem
        listener.onVideoToggle(video)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesVideoViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavoriteVideoItemBinding.inflate(inflater, parent, false)

        binding.apply {
            root.setOnClickListener(this@FavoritesVideoAdapter)
            playerContainer.setOnClickListener(this@FavoritesVideoAdapter)
            deleteVideoView.setOnClickListener(this@FavoritesVideoAdapter)
            checkbox.setOnClickListener(this@FavoritesVideoAdapter)
        }

        return FavoritesVideoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesVideoViewHolder, position: Int) {
        val videoItem = getItem(position) ?: return
        kohii.setUp(videoItem.videos.tiny.url) {
            tag = "${videoItem.videos.tiny.url}+${position}"
            log("${videoItem.videos.large.url}+${position}")
            repeatMode = Common.REPEAT_MODE_ONE
            preload = true
        }.bind(holder.playerContainer)
        holder.bind(getItem(position)!!)
    }

    inner class FavoritesVideoViewHolder(
        private val binding: FavoriteVideoItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val playerContainer = binding.exoPlayer

        fun bind(item: VideoListItem) = with(binding) {
            checkbox.tag = item
            deleteVideoView.tag = item
            root.tag = item
            playerContainer.tag = item
            tvDate.text = item.date.dateConverter()

            checkbox.isChecked = item.isChecked
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<VideoListItem>() {
        override fun areItemsTheSame(oldItem: VideoListItem, newItem: VideoListItem): Boolean =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: VideoListItem, newItem: VideoListItem): Boolean =
            oldItem == newItem

        override fun getChangePayload(oldItem: VideoListItem, newItem: VideoListItem): Any = ""
    }
}