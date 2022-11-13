@file:OptIn(FlowPreview::class)

package ui.dendi.finder.videos_presentation.videos

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.base.DefaultLoadStateAdapter
import ui.dendi.finder.core.core.extension.hideKeyboard
import ui.dendi.finder.core.core.extension.scrollToTop
import ui.dendi.finder.core.core.extension.showToast
import ui.dendi.finder.core.core.extension.simpleScan
import ui.dendi.finder.core.core.util.KohiiProvider
import ui.dendi.finder.videos_presentation.R
import ui.dendi.finder.videos_presentation.databinding.FragmentVideosBinding

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
            .addBucket(view = binding.recyclerView,
                strategy = Strategy.MULTI_PLAYER,
                selector = { candidates ->
                    candidates.take(2)
                }
            )

        val adapter = VideosPagingAdapter(
            kohii = kohii,
            toVideoDetails = {
                viewModel.launchDetailsScreen(it)
            },
            addToFavorite = {
                viewModel.addToFavorite(it)
                requireContext().showToast(getString(R.string.video_added_to_favorites))
            },
            shareVideo = {
                shareItem(it.user, it.pageURL)
            },
        )

        ivFilter.setOnClickListener {
            val imageFilterBottomDialog = VideoFilterBottomDialog()
            imageFilterBottomDialog.show(parentFragmentManager, imageFilterBottomDialog.tag)
        }

        searchEditText.setSearchTextChangedClickListener {
            viewModel.searchVideo(it)
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchBy.collectLatest {
                it?.let { searchEditText.setQuery(it) }
            }
        }

        recyclerView.adapter = adapter

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    recyclerView.hideKeyboard()
                    searchEditText.clearFocus()
                }
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.videosFlow.collectLatest { data ->
                adapter.submitData(data)
            }
        }

        recyclerView.scrollToTop(btnScrollToTop)

        setupList(adapter)
        observeState(adapter)
        setupRefreshLayout(adapter)
        handleScrollingToTop(adapter)
    }

    private fun setupList(adapter: VideosPagingAdapter) = with(binding) {
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as? DefaultItemAnimator)
            ?.supportsChangeAnimations = false
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                requireContext(),
                DividerItemDecoration.VERTICAL
            )
        )

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) recyclerView.hideKeyboard()
            }
        })

        lifecycleScope.launch {
            val footerAdapter = DefaultLoadStateAdapter { adapter.retry() }
            val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
            recyclerView.adapter = adapterWithLoadState
        }
    }

    private fun setupRefreshLayout(adapter: VideosPagingAdapter) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun observeState(adapter: VideosPagingAdapter) = with(binding) {
        val loadStateHolder = DefaultLoadStateAdapter.Holder(
            loadingState,
            swipeRefreshLayout
        ) { adapter.retry() }
        adapter.loadStateFlow
            .debounce(300)
            .onEach {
                loadStateHolder.bind(it.refresh)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleScrollingToTop(adapter: VideosPagingAdapter) = lifecycleScope.launch {
        getRefreshLoadState(adapter)
            .simpleScan(count = 2)
            .collect { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    delay(200)
                    binding.recyclerView.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadState(adapter: VideosPagingAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }
}