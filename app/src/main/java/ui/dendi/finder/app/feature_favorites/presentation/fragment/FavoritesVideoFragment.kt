package ui.dendi.finder.app.feature_favorites.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import ui.dendi.finder.app.R
import ui.dendi.finder.app.core.base.BaseFragment
import ui.dendi.finder.app.core.extension.collectWithLifecycle
import ui.dendi.finder.app.core.extension.showSnackbar
import ui.dendi.finder.app.core.util.KohiiProvider
import ui.dendi.finder.app.databinding.FragmentFavoritesVideoBinding
import ui.dendi.finder.app.feature_favorites.presentation.adapter.FavoritesVideoAdapter
import ui.dendi.finder.app.feature_favorites.presentation.viewmodel.FavoritesVideoViewModel

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
            kohii,
        ) {
            viewModel.deleteFromFavoritesVideo(it)
            showSnackbar("Video is deleted from your favorites")
        }

        collectWithLifecycle(viewModel.favoritesVideo) {
            videoAdapter.submitList(listOf())
        }
        rvVideosFavorite.adapter = videoAdapter
        btnDeleteAll.setOnClickListener {
            //TODO
        }
    }
}