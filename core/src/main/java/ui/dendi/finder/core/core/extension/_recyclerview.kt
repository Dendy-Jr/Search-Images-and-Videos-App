package ui.dendi.finder.core.core.extension

import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import ui.dendi.finder.core.core.models.ImagesColumnType
import ui.dendi.finder.core.core.models.VideosColumnType

fun RecyclerView.scrollToTop(button: View) {
    addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val manager = (recyclerView.layoutManager as? LinearLayoutManager) ?: return
            val needScroll = manager.findFirstVisibleItemPosition() > 0
            button.isVisible = needScroll

            button.setOnClickListener {
                if (needScroll) {
                    smoothScrollToPosition(0)
                }
            }
        }
    })
}

fun RecyclerView.setLayoutManager(imagesColumnType: ImagesColumnType) {
    this.layoutManager = when (imagesColumnType) {
        ImagesColumnType.ONE_COLUMN -> LinearLayoutManager(
            this.context,
            RecyclerView.VERTICAL,
            false
        )
        ImagesColumnType.TWO_COLUMNS -> StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)
        ImagesColumnType.THREE_COLUMNS -> StaggeredGridLayoutManager(3, RecyclerView.VERTICAL)
        ImagesColumnType.FOUR_COLUMNS -> StaggeredGridLayoutManager(4, RecyclerView.VERTICAL)
    }
}

fun RecyclerView.setLayoutManager(videosColumnType: VideosColumnType) {
    this.layoutManager = when (videosColumnType) {
        VideosColumnType.ONE_COLUMN -> LinearLayoutManager(
            this.context,
            RecyclerView.VERTICAL,
            false,
        )
        VideosColumnType.TWO_COLUMNS -> GridLayoutManager(
            this.context,
            2,
            RecyclerView.VERTICAL,
            false,
        )
    }
}