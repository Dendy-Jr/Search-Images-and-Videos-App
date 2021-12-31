package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.core.showSnackbar
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoEntity
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesVideoBinding
import com.dendi.android.search_images_and_videos_app.presentation.core.KohiiProvider
import com.dendi.android.search_images_and_videos_app.presentation.favorite.FavoritesVideoAdapter
import com.dendi.android.search_images_and_videos_app.presentation.favorite.FavoritesVideoViewModel
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Dendy-Jr on 29.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesVideoFragment : BaseFragment(R.layout.fragment_favorites_video) {

    private val binding by viewBinding(FragmentFavoritesVideoBinding::bind)
    override fun setRecyclerView() = binding.rvVideosFavorite
    private val viewModel by viewModel<FavoritesVideoViewModel>()

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

        val videoAdapter = FavoritesVideoAdapter(kohii, object : OnClickListener<VideoEntity> {
            override fun click(item: VideoEntity) {
                viewModel.deleteFromFavoritesVideo(item)
                showSnackbar("Video is deleted from your favorites")
            }
        })
        setAdapter(videoAdapter)

        collectLatestLifecycleFlow(viewModel.favoritesVideo) {
            videoAdapter.submitList(it)
        }
    }
}