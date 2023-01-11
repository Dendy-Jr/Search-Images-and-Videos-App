package ui.dendi.finder.favorites_presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ui.dendi.finder.core.core.extension.dateConverter
import ui.dendi.finder.core.core.extension.loadImage
import ui.dendi.finder.core.core.multichoice.ImageListItem
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.favorites_presentation.databinding.FavoriteImageItemBinding

class FavoritesImageAdapter(
    private val listener: ImageAdapterListener,
) : ListAdapter<ImageListItem, FavoritesImageAdapter.FavoritesImageViewHolder>(ItemCallback),
    View.OnClickListener, View.OnLongClickListener {

    override fun onClick(v: View) {
        val image = v.tag as ImageListItem
        when (v.id) {
            R.id.deleteImageView -> listener.onImageDelete(image)
            R.id.checkbox -> listener.onImageToggle(image)
            else -> listener.onImageChosen(image)
        }
    }

    override fun onLongClick(v: View): Boolean {
        val image = v.tag as ImageListItem
        listener.onImageToggle(image)
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = FavoriteImageItemBinding.inflate(inflater, parent, false)

        binding.apply {
            root.setOnClickListener(this@FavoritesImageAdapter)
            root.setOnLongClickListener(this@FavoritesImageAdapter)
            deleteImageView.setOnClickListener(this@FavoritesImageAdapter)
            checkbox.setOnClickListener(this@FavoritesImageAdapter)
        }

        return FavoritesImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoritesImageViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class FavoritesImageViewHolder(
        private val binding: FavoriteImageItemBinding,
    ) : ViewHolder(binding.root) {

        fun bind(item: ImageListItem) = with(binding) {
            imageView.loadImage(item.largeImageURL)

            checkbox.tag = item
            deleteImageView.tag = item
            root.tag = item
            tvDate.text = item.date.dateConverter()

            checkbox.isChecked = item.isChecked
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<ImageListItem>() {
        override fun areItemsTheSame(oldItem: ImageListItem, newItem: ImageListItem): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ImageListItem, newItem: ImageListItem): Boolean =
            oldItem == newItem

        override fun getChangePayload(oldItem: ImageListItem, newItem: ImageListItem): Any = ""
    }
}