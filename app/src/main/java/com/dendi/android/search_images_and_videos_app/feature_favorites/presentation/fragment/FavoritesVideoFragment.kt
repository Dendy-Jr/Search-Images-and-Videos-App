package com.dendi.android.search_images_and_videos_app.feature_favorites.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.base.BaseFragment
import com.dendi.android.search_images_and_videos_app.core.extension.collectWithLifecycle
import com.dendi.android.search_images_and_videos_app.core.extension.showSnackbar
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesVideoBinding
import com.dendi.android.search_images_and_videos_app.core.util.KohiiProvider
import com.dendi.android.search_images_and_videos_app.feature_favorites.presentation.adapter.FavoritesVideoAdapter
import com.dendi.android.search_images_and_videos_app.feature_favorites.presentation.viewmodel.FavoritesVideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy

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