@file:OptIn(FlowPreview::class)

package ui.dendi.finder.images_presentation.images

import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.base.DefaultLoadStateAdapter
import ui.dendi.finder.core.core.extension.*
import ui.dendi.finder.core.core.multichoice.ImageListItem
import ui.dendi.finder.images_presentation.R
import ui.dendi.finder.images_presentation.databinding.FragmentImagesBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchImagesFragment : BaseFragment<SearchImagesViewModel>(R.layout.fragment_images) {

    private val binding: FragmentImagesBinding by viewBinding()
    override val viewModel: SearchImagesViewModel by parentViewModel()

    private val adapter = SearchImagesPagingAdapter(
        object : SearchImagesPagingAdapter.ImageAdapterListener {
            override fun onImageChosen(image: ImageListItem) {
                viewModel.launchDetailScreen(image)
            }

            override fun onImageToggle(image: ImageListItem) {
                viewModel.onImageToggle(image)
            }
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

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.searchBy.collectLatest {
                it?.let { searchEditText.setQuery(it) }
            }
        }

        btnFilter.setOnClickListener {
            val imageFilterBottomDialog = ImageFilterBottomDialog()
            imageFilterBottomDialog.show(parentFragmentManager, imageFilterBottomDialog.tag)
        }

        recyclerView.scrollToTop(btnScrollToTop)

        clearAllMultiChoiceTextView.setOnClickListener {
            viewModel.clearAllMultiChoiceImages()
            btnAddToFavorite.isVisible = false
            adapter.notifyDataSetChanged()
        }

        collectWithLifecycle(viewModel.needShowAddToFavoriteButton) {
            btnAddToFavorite.isVisible = it
        }

        btnAddToFavorite.setOnClickListener {
            viewModel.addCheckedToFavorites()
        }

        //TODO Add the same logic added/deleted to search video screen, from favorite images and videos screen
        addToFavorite(recyclerView)

        collectImages()
        observeState()
        setupRefreshLayout()
        recyclerView.setupList(adapter, searchEditText)
    }

    private fun addToFavorite(recyclerView: RecyclerView) {
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                viewModel.addToFavorite(
                    adapter.getImageListItem(viewHolder.bindingAdapterPosition) ?: return
                )
                requireContext().showToast(getString(R.string.added_to_favorite))
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            adapter.refresh()
        }
    }

    private fun collectImages() = with(binding) {
        lifecycleScope.launch {
            viewModel.imagesState.filterNotNull().collectLatest { state ->
                adapter.submitData(state.pagingData)

                clearAllMultiChoiceTextView.setText(state.selectAllOperation.titleRes)
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
        return adapter.loadStateFlow.map { it.refresh }
    }
}