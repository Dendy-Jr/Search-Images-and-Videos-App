package ui.dendi.finder.favorites_presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.extension.collectWithLifecycle
import ui.dendi.finder.core.core.extension.showSnackbar
import ui.dendi.finder.core.core.util.KohiiProvider
import ui.dendi.finder.favorites_presentation.adapter.FavoritesVideoAdapter
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.favorites_presentation.databinding.FragmentFavoritesVideoBinding
import ui.dendi.finder.favorites_presentation.viewmodel.FavoritesVideoViewModel

@AndroidEntryPoint
class FavoritesVideoFragment :
    BaseFragment<FavoritesVideoViewModel>(R.layout.fragment_favorites_video) {

    private val binding: FragmentFavoritesVideoBinding by viewBinding()
    override val viewModel: FavoritesVideoViewModel by viewModels()

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
        ) {
            viewModel.deleteFromFavoritesVideo(it)
        }

        collectWithLifecycle(viewModel.favoriteVideos) {
            videoAdapter.submitList(it)
        }

        collectWithLifecycle(viewModel.needShowDeleteButton) { needShowButton ->
            btnDeleteAll.isVisible = needShowButton
        }

        rvVideosFavorite.adapter = videoAdapter
        btnDeleteAll.setOnClickListener {
            viewModel.clearAllVideos()
        }
    }
}