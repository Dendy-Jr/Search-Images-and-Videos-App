package ui.dendi.finder.images_presentation.images

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import ui.dendi.finder.core.core.extension.loadImage
import ui.dendi.finder.core.core.multichoice.ImageListItem
import ui.dendi.finder.images_presentation.R
import ui.dendi.finder.images_presentation.databinding.ImageItemMultipleColumnsBinding

class SearchImagesMultipleColumnsAdapter(
    private val listener: ImageAdapterListener,
) : PagingDataAdapter<ImageListItem, SearchImagesMultipleColumnsAdapter.SearchImagesPagingViewHolder>(
    ItemCallback
), View.OnClickListener, View.OnLongClickListener {

    override fun onClick(v: View) {
        val image = v.tag as ImageListItem
        when (v.id) {
            //TODO change `when` to `if`
            R.id.container -> listener.onImageChosen(image)
        }
    }

    override fun onLongClick(v: View): Boolean {
        val image = v.tag as ImageListItem
        listener.addToFavorite(image)
        return true
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): SearchImagesPagingViewHolder {
        val inflate = LayoutInflater.from(parent.context)
        val binding = ImageItemMultipleColumnsBinding.inflate(inflate, parent, false)

        binding.apply {
            root.setOnClickListener(this@SearchImagesMultipleColumnsAdapter)
            root.setOnLongClickListener(this@SearchImagesMultipleColumnsAdapter)
        }
        return SearchImagesPagingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchImagesPagingViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class SearchImagesPagingViewHolder(
        private val binding: ImageItemMultipleColumnsBinding,
    ) : ViewHolder(binding.root) {

        fun bind(item: ImageListItem) = with(binding) {
            imageView.loadImage(item.largeImageURL)
            root.tag = item

            // TODO move `share` into details screen
        }
    }

    companion object ItemCallback : DiffUtil.ItemCallback<ImageListItem>() {
        override fun areItemsTheSame(oldItem: ImageListItem, newItem: ImageListItem) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ImageListItem, newItem: ImageListItem) =
            oldItem == newItem

        override fun getChangePayload(oldItem: ImageListItem, newItem: ImageListItem): Any = ""
    }

    //TODO duplicate
    interface ImageAdapterListener {
        fun onImageChosen(image: ImageListItem)
        fun addToFavorite(image: ImageListItem)
    }
}