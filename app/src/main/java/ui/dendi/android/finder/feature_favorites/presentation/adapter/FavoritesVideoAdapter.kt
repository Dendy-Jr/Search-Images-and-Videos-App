package ui.dendi.android.finder.feature_favorites.presentation.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import kohii.v1.core.Common
import kohii.v1.exoplayer.Kohii
import ui.dendi.android.finder.databinding.FavoriteVideoItemBinding
import ui.dendi.android.finder.feature_videos.domain.Video

class FavoritesVideoAdapter(
    private val kohii: Kohii,
    private val deleteFromFavorite: (Video) -> Unit,
) : ListAdapter<Video, FavoritesVideoAdapter.FavoritesVideoViewHolder>(ItemCallback) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteVideoItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).let(::FavoritesVideoViewHolder)

    override fun onBindViewHolder(holder: FavoritesVideoViewHolder, position: Int) {
        val videoItem = getItem(position) ?: return
        kohii.setUp(videoItem.videos.tiny.url) {
            tag = "${videoItem.videos.tiny.url}+${position}"
            Log.d("kohii", "${videoItem.videos.large.url}+${position}")
            repeatMode = Common.REPEAT_MODE_ONE
            preload = true
        }.bind(holder.playerContainer)
        holder.bind(getItem(position)!!)
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipe, videoItem.id.toString())
        viewBinderHelper.closeLayout(videoItem.id.toString())
    }

    inner class FavoritesVideoViewHolder(
        private val binding: FavoriteVideoItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val swipe = binding.swipeRevealLayout
        val playerContainer = binding.playerContainer

        fun bind(item: Video) = with(binding) {
            ibDelete.setOnClickListener {
                item.isFavorite = false
                deleteFromFavorite.invoke(item)
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