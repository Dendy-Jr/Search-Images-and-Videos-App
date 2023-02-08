@file:OptIn(FlowPreview::class)

package ui.dendi.finder.images_presentation.images

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.*
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
import ui.dendi.finder.core.core.models.ImagesColumnType
import ui.dendi.finder.core.core.multichoice.ImageListItem
import ui.dendi.finder.core.core.theme.applyTextColorGradient
import ui.dendi.finder.images_presentation.R
import ui.dendi.finder.images_presentation.databinding.FragmentImagesBinding

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchImagesFragment : BaseFragment<SearchImagesViewModel>(R.layout.fragment_images) {

    private val binding: FragmentImagesBinding by viewBinding()
    override val viewModel: SearchImagesViewModel by parentViewModel()

    private val singleColumnAdapter = SearchImagesSingleColumnAdapter(
        object : SearchImagesSingleColumnAdapter.ImageAdapterListener {
            override fun onImageChosen(image: ImageListItem) {
                viewModel.launchDetailScreen(image)
            }

            override fun onImageToggle(image: ImageListItem) {
                viewModel.onImageToggle(image)
            }

            override fun addToFavorite(image: ImageListItem) {
                viewModel.addToFavorite(image)
                requireContext().showToast(R.string.added_to_favorite)
            }
        }
    )

    private val multipleColumnsAdapter = SearchImagesMultipleColumnsAdapter(
        object : SearchImagesMultipleColumnsAdapter.ImageAdapterListener {
            override fun onImageChosen(image: ImageListItem) {
                viewModel.launchDetailScreen(image)
            }

            override fun addToFavorite(image: ImageListItem) {
                viewModel.addToFavorite(image)
                requireContext().showToast(R.string.added_to_favorite)
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    @SuppressLint("NotifyDataSetChanged")
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
        clearAllMultiChoiceTextView.apply {
            applyTextColorGradient()
            setOnClickListener {
                viewModel.clearAllMultiChoiceImages()
                btnAddToFavorite.isVisible = false
                singleColumnAdapter.notifyDataSetChanged()
            }
        }

        collectWithLifecycle(viewModel.needShowAddToFavoriteButton) {
            btnAddToFavorite.isVisible = it
        }

        btnAddToFavorite.setOnClickListener {
            viewModel.addCheckedToFavorites()
            viewModel.clearAllMultiChoiceImages()
        }

        collectWithLifecycle(viewModel.imagesColumnType) { imagesColumnType ->
            clearAllMultiChoiceTextView.isVisible =
                if (imagesColumnType == ImagesColumnType.ONE_COLUMN) {
                    true
                } else {
                    root.applyConstraint {
                        topToBottom(swipeRefreshLayout, searchEditText, 8)
                    }
                    false
                }

            val setAdapter = when (imagesColumnType) {
                ImagesColumnType.ONE_COLUMN -> singleColumnAdapter
                ImagesColumnType.TWO_COLUMNS -> multipleColumnsAdapter
                ImagesColumnType.THREE_COLUMNS -> multipleColumnsAdapter
                ImagesColumnType.FOUR_COLUMNS -> multipleColumnsAdapter
            }
            recyclerView.setLayoutManager(imagesColumnType)
            recyclerView.setupList(setAdapter, searchEditText)
        }

        collectImages()
        observeState()
        setupRefreshLayout()
    }

    private fun setupRefreshLayout() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            singleColumnAdapter.refresh()
            multipleColumnsAdapter.refresh()
        }
    }

    private fun collectImages() = with(binding) {
        lifecycleScope.launch {
            viewModel.imagesState.filterNotNull().collectLatest { state ->
                lifecycleScope.launch { singleColumnAdapter.submitData(state.pagingData) }
                lifecycleScope.launch { multipleColumnsAdapter.submitData(state.pagingData) }
                clearAllMultiChoiceTextView.setText(state.selectAllOperation.titleRes)
            }
        }
    }

    private fun observeState() = with(binding) {
        val loadStateHolder = DefaultLoadStateAdapter.Holder(
            loadingState,
            swipeRefreshLayout
        ) {
            singleColumnAdapter.retry()
            multipleColumnsAdapter.retry()
        }
        singleColumnAdapter.loadStateFlow
            .debounce(300)
            .onEach {
                loadStateHolder.bind(it.refresh)
            }
            .launchIn(lifecycleScope)

        multipleColumnsAdapter.loadStateFlow
            .debounce(300)
            .onEach {
                loadStateHolder.bind(it.refresh)
            }
            .launchIn(lifecycleScope)
    }

    private fun handleListVisibility() = lifecycleScope.launch {
        getRefreshLoadState(singleColumnAdapter)
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
        getRefreshLoadState(singleColumnAdapter)
            .simpleScan(count = 2)
            .collect { (previousState, currentState) ->
                if (previousState is LoadState.Loading && currentState is LoadState.NotLoading) {
                    delay(200)
                    binding.recyclerView.scrollToPosition(0)
                }
            }
    }

    private fun getRefreshLoadState(adapter: SearchImagesSingleColumnAdapter): Flow<LoadState> {
        adapter.loadStateFlow.map { it.refresh }
        return adapter.loadStateFlow.map { it.refresh }
    }
}