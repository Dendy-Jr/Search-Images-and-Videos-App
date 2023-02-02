@file:OptIn(FlowPreview::class)

package ui.dendi.finder.videos_presentation.videos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
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
import ui.dendi.finder.core.core.extension.*
import ui.dendi.finder.core.core.multichoice.VideoListItem
import ui.dendi.finder.core.core.theme.applyTextColorGradient
import ui.dendi.finder.core.core.util.KohiiProvider
import ui.dendi.finder.videos_presentation.R
import ui.dendi.finder.videos_presentation.databinding.FragmentVideosBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchVideosFragment : BaseFragment<SearchVideosViewModel>(R.layout.fragment_videos) {

    private val binding: FragmentVideosBinding by viewBinding()
    override val viewModel: SearchVideosViewModel by parentViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun onBind() = with(binding) {
        val kohii = KohiiProvider.get(requireContext())
        kohii.register(this@SearchVideosFragment, memoryMode = MemoryMode.BALANCED)
            .addBucket(view = binding.recyclerView,
                strategy = Strategy.MULTI_PLAYER,
                selector = { candidates ->
                    candidates.take(3)
                }
            )

        val adapter = VideosPagingAdapter(
            kohii = kohii,
            listener = object : VideosPagingAdapter.VideoAdapterListener {
                override fun onVideoChose(video: VideoListItem) {
                    viewModel.launchDetailsScreen(video)
                }

                override fun onVideoToggle(video: VideoListItem) {
                    viewModel.onVideoToggle(video)
                }
            }
        )

        btnFilter.setOnClickListener {
            val imageFilterBottomDialog = VideoFilterBottomDialog()
            imageFilterBottomDialog.show(parentFragmentManager, imageFilterBottomDialog.tag)
        }

        clearAllMultiChoiceTextView.apply {
            applyTextColorGradient()
            setOnClickListener {
                viewModel.clearAllMultiChoiceVideos()
                btnAddToFavorite.isVisible = false
                adapter.notifyDataSetChanged()
            }
        }


        collectWithLifecycle(viewModel.needShowAddToFavoriteButton) {
            btnAddToFavorite.isVisible = it
        }

        btnAddToFavorite.setOnClickListener {
            viewModel.addCheckedToFavorites()
            viewModel.clearAllMultiChoiceVideos()
        }

//        collectWithLifecycle(viewModel.listColumnType) {
//            recyclerView.setLayoutManager(it)
//        }

        searchEditText.setSearchTextChangedClickListener {
            viewModel.setSearchBy(it)
        }

        collectWithLifecycle(viewModel.searchBy) {
            it?.let { searchEditText.setQuery(it) }
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

        recyclerView.scrollToTop(btnScrollToTop)

        collectVideos(adapter)
        observeState(adapter)
        setupRefreshLayout(adapter)
        recyclerView.setupList(adapter, searchEditText)
    }

    private fun setupRefreshLayout(adapter: VideosPagingAdapter) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun collectVideos(adapter: VideosPagingAdapter) = with(binding) {
        lifecycleScope.launch {
            viewModel.videosState.filterNotNull().collectLatest { state ->
                adapter.submitData(state.pagingData)
                clearAllMultiChoiceTextView.setText(state.selectAllOperation.titleRes)
            }
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