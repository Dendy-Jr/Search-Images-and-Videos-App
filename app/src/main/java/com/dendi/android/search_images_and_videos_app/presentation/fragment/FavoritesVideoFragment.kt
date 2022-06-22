package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.DialogManagerImpl
import com.dendi.android.search_images_and_videos_app.core.extension.showSnackbar
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesVideoBinding
import com.dendi.android.search_images_and_videos_app.presentation.core.KohiiProvider
import com.dendi.android.search_images_and_videos_app.presentation.adapter.FavoritesVideoAdapter
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesVideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy

@AndroidEntryPoint
class FavoritesVideoFragment : BaseFragment(R.layout.fragment_favorites_video) {

    private val binding by viewBinding(FragmentFavoritesVideoBinding::bind)
    override fun setRecyclerView() = binding.rvVideosFavorite
    private val viewModel by viewModels<FavoritesVideoViewModel>()

    private val dialog by lazy { DialogManagerImpl(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kohii = KohiiProvider.get(requireContext())
        kohii.register(this, memoryMode = MemoryMode.BALANCED)
            .addBucket(
                view = binding.rvVideosFavorite,
                strategy = Strategy.MULTI_PLAYER,
                selector = { candidates ->
                    candidates.take(3)
                }
            )

        val videoAdapter = FavoritesVideoAdapter(
            kohii,
        ) {
            viewModel.deleteFromFavoritesVideo(it)
            showSnackbar("Video is deleted from your favorites")
        }
        setAdapter(videoAdapter)

        collectWithLifecycle(viewModel.favoritesVideo) {
            videoAdapter.submitList(listOf())
        }

        binding.btnDeleteAll.setOnClickListener {
            dialog.showDeleteDialog(viewModel)
        }
    }
}