package com.dendi.android.search_images_and_videos_app.feature_videos.presentation.videos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.base.BaseFragment
import com.dendi.android.search_images_and_videos_app.core.extension.collectWithLifecycle
import com.dendi.android.search_images_and_videos_app.core.extension.showSnackbar
import com.dendi.android.search_images_and_videos_app.databinding.FragmentVideosBinding
import com.dendi.android.search_images_and_videos_app.core.util.KohiiProvider
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchVideosFragment : BaseFragment<SearchVideosViewModel>(R.layout.fragment_videos) {

    private val binding: FragmentVideosBinding by viewBinding()
    override val viewModel: SearchVideosViewModel by viewModels()
    override fun setRecyclerView(): RecyclerView = binding.recyclerViewVideo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        val kohii = KohiiProvider.get(requireContext())
        kohii.register(this@SearchVideosFragment, memoryMode = MemoryMode.BALANCED)
            .addBucket(view = binding.recyclerViewVideo,
                strategy = Strategy.MULTI_PLAYER,
                selector = { candidates ->
                    candidates.take(2)
                }
            )

        val videoAdapter = VideosPagingAdapter(
            kohii,
            {
                viewModel.launchDetailsScreen(it)
            },
            {
                viewModel.addToFavorite(it)
                showSnackbar("Video is added to your favorites")
            },
            {
                shareItem(it.user, it.pageURL)
            },
        )

        searchEditText.setSearchTextChangedClickListener {
            viewModel.searchVideo(it)
        }

        setAdapter(videoAdapter)
        collectWithLifecycle(viewModel.videosFlow) { data ->
            lifecycleScope.launch {
                videoAdapter.submitData(data)
            }
        }
    }
}

