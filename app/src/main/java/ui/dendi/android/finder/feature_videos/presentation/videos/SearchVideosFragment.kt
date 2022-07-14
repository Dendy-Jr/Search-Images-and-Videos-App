package ui.dendi.android.finder.feature_videos.presentation.videos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ui.dendi.android.finder.R
import ui.dendi.android.finder.core.base.BaseFragment
import ui.dendi.android.finder.core.extension.showSnackbar
import ui.dendi.android.finder.core.util.KohiiProvider
import ui.dendi.android.finder.databinding.FragmentVideosBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchVideosFragment : BaseFragment<SearchVideosViewModel>(R.layout.fragment_videos) {

    private val binding: FragmentVideosBinding by viewBinding()
    override val viewModel: SearchVideosViewModel by viewModels()

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
            kohii = kohii,
            toVideoDetails = {
                viewModel.launchDetailsScreen(it)
            },
            addToFavorite = {
                viewModel.addToFavorite(it)
                showSnackbar("Video is added to your favorites")
            },
            shareVideo = {
                shareItem(it.user, it.pageURL)
            },
        )

        searchEditText.setSearchTextChangedClickListener {
            viewModel.searchVideo(it)
        }

        recyclerViewVideo.adapter = videoAdapter
//        collectWithLifecycle(viewModel.videosFlow) { data ->
//                videoAdapter.submitData(data)
//        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videosFlow.collectLatest {
                videoAdapter.submitData(it)
            }
        }
    }
}

