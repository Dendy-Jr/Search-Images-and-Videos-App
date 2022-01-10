package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.core.afterTextChanged
import com.dendi.android.search_images_and_videos_app.core.showSnackbar
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import com.dendi.android.search_images_and_videos_app.databinding.FragmentVideosBinding
import com.dendi.android.search_images_and_videos_app.presentation.adapter.VideoPagingAdapter
import com.dendi.android.search_images_and_videos_app.presentation.core.KohiiProvider
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.VideoViewModel
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.serialization.ExperimentalSerializationApi
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Dendy-Jr on 12.12.2021
 * olehvynnytskyi@gmail.com
 */

@ExperimentalSerializationApi
@ExperimentalCoroutinesApi
class VideosFragment : BaseFragment(R.layout.fragment_videos) {

    private val binding by viewBinding(FragmentVideosBinding::bind)
    private val viewModel by viewModel<VideoViewModel>()
    override fun setRecyclerView(): RecyclerView = binding.recyclerViewVideo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kohii = KohiiProvider.get(requireContext())
        kohii.register(this, memoryMode = MemoryMode.BALANCED)
            .addBucket(view = binding.recyclerViewVideo,
                strategy = Strategy.MULTI_PLAYER,
                selector = { candidates ->
                    candidates.take(2)
                }
            )

        val videoAdapter = VideoPagingAdapter(kohii, object : OnClickListener<VideoCache> {
            override fun click(item: VideoCache) {
                val direction =
                    VideosFragmentDirections.actionVideosFragmentToVideoDetailsFragment(item)
                navController.navigate(direction)
            }
        },
            object : OnClickListener<VideoCache> {
                override fun click(item: VideoCache) {
                    viewModel.addToFavorite(item)
                    showSnackbar("Video is added to your favorites")
                }
            },
            object : OnClickListener<VideoCache> {
                override fun click(item: VideoCache) {
                    shareItem(item.user, item.pageURL)
                }
            }
        )
        binding.run {
            searchInput.requestFocus()
            searchInput.afterTextChanged(viewModel::searchVideo)
        }

        setAdapter(videoAdapter)
        collectLatestLifecycleFlow(viewModel.videos) { data ->
            videoAdapter.submitData(data)
        }
    }
}

