package ui.dendi.finder.images_presentation.images

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chauthai.swipereveallayout.ViewBinderHelper
import ui.dendi.finder.core.core.extension.loadImageOriginal
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.images_presentation.databinding.ImageItemBinding

class SearchImagesPagingAdapter(
    private val toImage: (Image) -> Unit,
    private val addToFavorite: (Image) -> Unit,
    private val shareImage: (Image) -> Unit,
) : PagingDataAdapter<Image, SearchImagesPagingAdapter.SearchImagesPagingViewHolder>(ItemCallback) {

    private val viewBinderHelper = ViewBinderHelper()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ImageItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        ).let(::SearchImagesPagingViewHolder)

    override fun onBindViewHolder(holder: SearchImagesPagingViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            viewBinderHelper.setOpenOnlyOne(true)
            viewBinderHelper.bind(holder.swipe, currentItem.id.toString())
            viewBinderHelper.closeLayout(currentItem.id.toString())
        }
    }

    inner class SearchImagesPagingViewHolder(
        private val binding: ImageItemBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        val swipe = binding.swipeRevealLayout

        fun bind(item: Image) = with(binding) {
            imageView.loadImageOriginal(item.largeImageURL)

            imageView.setOnClickListener {
                toImage.invoke(item)
            }

            btnAdd.setOnClickListener {
                item.isFavorite = true
                addToFavorite.invoke(item)
                swipe.close(false)
            }

            btnShare.setOnClickListener {
                shareImage.invoke(item)
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