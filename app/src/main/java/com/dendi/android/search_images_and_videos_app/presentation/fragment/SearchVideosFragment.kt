package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.extension.afterTextChanged
import com.dendi.android.search_images_and_videos_app.core.extension.showSnackbar
import com.dendi.android.search_images_and_videos_app.databinding.FragmentVideosBinding
import com.dendi.android.search_images_and_videos_app.presentation.adapter.VideoPagingAdapter
import com.dendi.android.search_images_and_videos_app.presentation.core.KohiiProvider
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.SearchVideoViewModel
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchVideosFragment : BaseFragment(R.layout.fragment_videos) {

    private val binding by viewBinding(FragmentVideosBinding::bind)
    private val viewModel by viewModels<SearchVideoViewModel>()
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

        val videoAdapter = VideoPagingAdapter(
            kohii,
            {
                val direction =
                    SearchVideosFragmentDirections.actionVideosFragmentToVideoDetailsFragment(it)
                navController.navigate(direction)
            },
            {
                viewModel.addToFavorite(it)
                showSnackbar("Video is added to your favorites")
            },
            {
                shareItem(it.user, it.pageURL)
            },
        )
        binding.run {
            searchInput.requestFocus()
            searchInput.afterTextChanged(viewModel::searchVideo)
        }

        setAdapter(videoAdapter)
        collectWithLifecycle(viewModel.videos) { data ->
            videoAdapter.submitData(data)
        }
    }
}

