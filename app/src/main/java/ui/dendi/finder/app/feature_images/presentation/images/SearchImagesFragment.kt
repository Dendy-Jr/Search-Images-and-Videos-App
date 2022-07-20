package ui.dendi.finder.app.feature_images.presentation.images

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.app.R
import ui.dendi.finder.app.core.base.BaseFragment
import ui.dendi.finder.app.core.base.DefaultLoadStateAdapter
import ui.dendi.finder.app.core.extension.showToast
import ui.dendi.finder.app.core.extension.simpleScan
import ui.dendi.finder.app.databinding.FragmentImagesBinding

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchImagesFragment : BaseFragment<SearchImagesViewModel>(R.layout.fragment_images) {

    private val binding: FragmentImagesBinding by viewBinding()
    override val viewModel: SearchImagesViewModel by viewModels()

    private val adapter = SearchImagesPagingAdapter(
        toImage = {
            viewModel.launchDetailScreen(it)
        },
        addToFavorite = {
            viewModel.addToFavorite(it)
            requireContext().showToast(getString(R.string.added_to_favorites))
        },
        shareImage = {
            shareItem(it.user, it.pageURL)
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        searchEditText.setSearchTextChangedClickListener {
            viewModel.setSearchBy(it)
        }
//        imagesStorage.query?.let { searchEditText.setLastQuery(it) }

//        collectWithLifecycle(viewModel.searchBy) {
//            it?.let { searchEditText.setLastQuery(it) }
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchBy.collectLatest {
                it?.let { searchEditText.setQuery(it) }
            }
        }

        setupList()
        setupRefreshLayout()

        collectImages()
        observeState()

        handleListVisibility()
        handleScrollingToTop()
    }

    private fun setupList() = with(binding) {
        recyclerView.adapter = adapter
        (recyclerView.itemAnimator as? DefaultItemAnimator)
            ?.supportsChangeAnimations = false
        recyclerView.addItemDecoration(DividerItemDecoration(requireContext(), VERTICAL))

        lifecycleScope.launch {
            waiteForLoad()
            val footerAdapter = DefaultLoadStateAdapter { adapter.retry() }
            val adapterWithLoadState = adapter.withLoadStateFooter(footerAdapter)
            recyclerView.adapter = adapterWithLoadState
        }
    }

    private suspend fun waiteForLoad() {
        adapter.onPagesUpdatedFlow
            .map { adapter.itemCount }
            .first { it > 0 }
    }

    private fun setupRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun collectImages() {
//        collectWithLifecycle(viewModel.imagesFlow) {
//            adapter.submitData(it)
//        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.imagesFlow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun observeState() = with(binding) {
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

    private fun handleListVisibility() = lifecycleScope.launch {
        getRefreshLoadState(adapter)
            .simpleScan(count = 3)
            .collectLatest { (beforePrevious, previous, current) ->
                binding.recyclerView.isInvisible = current is LoadState.Error
                        || previous is LoadState.Error
                        || (beforePrevious is LoadState.Error
                        && previous is LoadState.NotLoading
                        && current is LoadState.Loading)
            }
    }

    private fun handleScrollingToTop() = lifecycleScope.launch {
        getRefreshLoadState(adapter)
            .simpleScan(count = 2)
            .collect { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    delay(200)
                    binding.recyclerView.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadState(adapter: SearchImagesPagingAdapter): Flow<LoadState> {
        return adapter.loadStateFlow
            .map { it.refresh }
    }
}