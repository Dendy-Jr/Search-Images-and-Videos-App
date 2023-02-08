@file:OptIn(FlowPreview::class)

package ui.dendi.finder.videos_presentation.videos

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
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
import ui.dendi.finder.core.core.models.VideosColumnType
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
                    candidates.take(6)
                })

        val singleColumnAdapter = SearchVideosSingleColumnAdapter(kohii = kohii,
            listener = object : SearchVideosSingleColumnAdapter.VideoAdapterListener {
                override fun onVideoChose(video: VideoListItem) {
                    viewModel.launchDetailsScreen(video)
                }

                override fun onVideoToggle(video: VideoListItem) {
                    viewModel.onVideoToggle(video)
                }
            })

        val multipleColumnsAdapter = SearchVideosMultipleColumnsAdapter(kohii = kohii,
            listener = object : SearchVideosMultipleColumnsAdapter.VideoAdapterListener {
                override fun onVideoChose(video: VideoListItem) {
                    viewModel.launchDetailsScreen(video)
                }

                override fun addToFavorite(video: VideoListItem) {
                    viewModel.addToFavorite(video)
                }
            })

        btnFilter.setOnClickListener {
            val imageFilterBottomDialog = VideoFilterBottomDialog()
            imageFilterBottomDialog.show(parentFragmentManager, imageFilterBottomDialog.tag)
        }

        clearAllMultiChoiceTextView.apply {
            applyTextColorGradient()
            setOnClickListener {
                viewModel.clearAllMultiChoiceVideos()
                btnAddToFavorite.isVisible = false
                singleColumnAdapter.notifyDataSetChanged()
            }
        }

        collectWithLifecycle(viewModel.needShowAddToFavoriteButton) {
            btnAddToFavorite.isVisible = it
        }

        btnAddToFavorite.setOnClickListener {
            viewModel.addCheckedToFavorites()
            viewModel.clearAllMultiChoiceVideos()
        }

        collectWithLifecycle(viewModel.videosColumnType) { videosColumnType ->
            clearAllMultiChoiceTextView.isVisible = videosColumnType == VideosColumnType.ONE_COLUMN
            when (videosColumnType) {
                VideosColumnType.ONE_COLUMN -> {
                    swipeRefreshLayout.updateLayoutParams { height = 0.dp }
                }
                VideosColumnType.TWO_COLUMNS -> {
                    twoColumnsVideosDesign()
                }
            }

            val setAdapter = when (videosColumnType) {
                VideosColumnType.ONE_COLUMN -> {
                    singleColumnAdapter
                }
                VideosColumnType.TWO_COLUMNS -> {
                    multipleColumnsAdapter
                }
            }
            recyclerView.setupList(setAdapter, searchEditText)
            recyclerView.setLayoutManager(videosColumnType)
        }

        searchEditText.setSearchTextChangedClickListener {
            viewModel.setSearchBy(it)
        }

        collectWithLifecycle(viewModel.searchBy) {
            it?.let { searchEditText.setQuery(it) }
        }

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    recyclerView.hideKeyboard()
                    searchEditText.clearFocus()
                }
            }
        })

        recyclerView.scrollToTop(btnScrollToTop)
        collectVideos(singleAdapter = singleColumnAdapter, multipleAdapter = multipleColumnsAdapter)
        observeState(singleAdapter = singleColumnAdapter, multipleAdapter = multipleColumnsAdapter)
        setupRefreshLayout(
            singleAdapter = singleColumnAdapter, multipleAdapter = multipleColumnsAdapter
        )
    }

    private fun twoColumnsVideosDesign() = with(binding) {
        swipeRefreshLayout.margin(left = 10F, top = 8F, right = 10F)
        btnFilter.margin(top = 16F, bottom = 0F)
        btnScrollToTop.margin(top = 16F, bottom = 0F)
        btnFilter.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = searchEditText.id
            bottomToBottom = ConstraintLayout.LayoutParams.UNSET
        }
        btnScrollToTop.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = searchEditText.id
            bottomToBottom = ConstraintLayout.LayoutParams.UNSET
        }
        swipeRefreshLayout.updateLayoutParams<ConstraintLayout.LayoutParams> {
            height = 300.dp
            topToBottom = btnFilter.id
            startToStart = container.id
            endToEnd = container.id
            bottomToBottom = ConstraintLayout.LayoutParams.UNSET
        }
    }

    private fun setupRefreshLayout(
        singleAdapter: SearchVideosSingleColumnAdapter,
        multipleAdapter: SearchVideosMultipleColumnsAdapter,
    ) {
        binding.swipeRefreshLayout.setOnRefreshListener {
            singleAdapter.refresh()
            multipleAdapter.refresh()
        }
    }

    private fun collectVideos(
        singleAdapter: SearchVideosSingleColumnAdapter,
        multipleAdapter: SearchVideosMultipleColumnsAdapter,
    ) = with(binding) {
        lifecycleScope.launch {
            viewModel.videosState.filterNotNull().collectLatest { state ->
                lifecycleScope.launch { singleAdapter.submitData(state.pagingData) }
                lifecycleScope.launch { multipleAdapter.submitData(state.pagingData) }
                clearAllMultiChoiceTextView.setText(state.selectAllOperation.titleRes)
            }
        }
    }

    private fun observeState(
        singleAdapter: SearchVideosSingleColumnAdapter,
        multipleAdapter: SearchVideosMultipleColumnsAdapter,
    ) = with(binding) {
        val loadStateHolder = DefaultLoadStateAdapter.Holder(
            loadingState, swipeRefreshLayout
        ) {
            singleAdapter.retry()
            multipleAdapter.retry()
        }
        singleAdapter.loadStateFlow.debounce(300).onEach {
            loadStateHolder.bind(it.refresh)
        }.launchIn(lifecycleScope)
        multipleAdapter.loadStateFlow.debounce(300).onEach {
            loadStateHolder.bind(it.refresh)
        }.launchIn(lifecycleScope)
    }

    private fun handleScrollingToTop(adapter: SearchVideosSingleColumnAdapter) =
        lifecycleScope.launch {
            getRefreshLoadState(adapter).simpleScan(count = 2)
                .collect { (previousState, currentState) ->
                    if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                        delay(200)
                        binding.recyclerView.scrollToPosition(0)
                    }
                }
        }

    private fun getRefreshLoadState(adapter: SearchVideosSingleColumnAdapter): Flow<LoadState> {
        return adapter.loadStateFlow.map { it.refresh }
    }
}