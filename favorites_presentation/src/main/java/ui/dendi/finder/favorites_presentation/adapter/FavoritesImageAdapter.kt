package ui.dendi.finder.favorites_presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import ui.dendi.finder.core.core.extension.loadImage
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.favorites_presentation.databinding.FavoriteImageItemBinding

class FavoritesImageAdapter(
    private val deleteFromFavorite: (Image) -> Unit,
) : ListAdapter<Image, FavoritesImageAdapter.FavoritesImageViewHolder>(ItemCallback) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        FavoriteImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false,
        ).let(::FavoritesImageViewHolder)

    override fun onBindViewHolder(holder: FavoritesImageViewHolder, position: Int) {
        holder.bind(getItem(position))
        viewBinderHelper.setOpenOnlyOne(true)
        viewBinderHelper.bind(holder.swipe, getItem(position).id.toString())
        viewBinderHelper.closeLayout(getItem(position).id.toString())
    }

    inner class FavoritesImageViewHolder(
        private val binding: FavoriteImageItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val swipe = binding.swipeRevealLayout

        fun bind(item: Image) = with(binding) {
            imageView.loadImage(item.largeImageURL)

            ibDelete.setOnClickListener {
                item.isFavorite = false
                deleteFromFavorite.invoke(item)
                swipe.close(false)
            }
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<Image>() {
        override fun areItemsTheSame(oldItem: Image, newItem: Image) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Image, newItem: Image) =
            oldItem == newItem
    }
}