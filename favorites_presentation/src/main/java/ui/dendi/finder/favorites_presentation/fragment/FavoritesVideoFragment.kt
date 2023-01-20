package ui.dendi.finder.favorites_presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.extension.collectWithLifecycle
import ui.dendi.finder.core.core.extension.parentViewModel
import ui.dendi.finder.core.core.multichoice.VideoListItem
import ui.dendi.finder.core.core.util.KohiiProvider
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.favorites_presentation.adapter.FavoritesVideoAdapter
import ui.dendi.finder.favorites_presentation.adapter.VideoAdapterListener
import ui.dendi.finder.favorites_presentation.databinding.FragmentFavoritesVideoBinding
import ui.dendi.finder.favorites_presentation.viewmodel.FavoritesVideoViewModel

@AndroidEntryPoint
class FavoritesVideoFragment :
    BaseFragment<FavoritesVideoViewModel>(R.layout.fragment_favorites_video) {

    private val binding: FragmentFavoritesVideoBinding by viewBinding()
    override val viewModel: FavoritesVideoViewModel by parentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        val kohii = KohiiProvider.get(requireContext())
        kohii.register(this@FavoritesVideoFragment, memoryMode = MemoryMode.BALANCED)
            .addBucket(
                view = rvVideosFavorite,
                strategy = Strategy.MULTI_PLAYER,
                selector = { candidates ->
                    candidates.take(2)
                }
            )

        val videoAdapter = FavoritesVideoAdapter(
            kohii = kohii,
            listener = object : VideoAdapterListener {
                override fun onVideoDelete(video: VideoListItem) {
                    viewModel.deleteFromFavoritesVideo(video)
                }

                override fun onVideoChosen(video: VideoListItem) {
                    // TODO navigate to details screen
                }

                override fun onVideoToggle(video: VideoListItem) {
                    viewModel.onVideoToggle(video)
                }
            }
        )

        collectWithLifecycle(viewModel.favoriteVideos) { state ->
            videoAdapter.submitList(state.videos)

            selectOrClearAllTextView.isVisible = state.videos.isNotEmpty()
            selectionStateTextView.isVisible = state.videos.isNotEmpty()

            selectOrClearAllTextView.setText(state.selectAllOperation.titleRes)
            selectionStateTextView.text = getString(
                R.string.selection_state,
                state.totalCheckedCount, state.totalCount
            )
        }

        collectWithLifecycle(viewModel.needShowDeleteButton) { needShowButton ->
            btnDeleteAll.isVisible = needShowButton
        }

        selectOrClearAllTextView.setOnClickListener {
            viewModel.selectOrClearAll()
        }

        rvVideosFavorite.adapter = videoAdapter
        btnDeleteAll.setOnClickListener {
            viewModel.clearAllVideos()
        }
    }
}