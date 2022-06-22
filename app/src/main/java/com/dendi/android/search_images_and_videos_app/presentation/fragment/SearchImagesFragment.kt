package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.extension.afterTextChanged
import com.dendi.android.search_images_and_videos_app.core.extension.isVisible
import com.dendi.android.search_images_and_videos_app.core.extension.showSnackbar
import com.dendi.android.search_images_and_videos_app.presentation.ImagesUIState
import com.dendi.android.search_images_and_videos_app.databinding.FragmentImagesBinding
import com.dendi.android.search_images_and_videos_app.presentation.adapter.ImagePagingAdapter
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.SearchImageViewModel
import com.dendi.android.search_images_and_videos_app.presentation.adapter.LoadAdapter
import com.dendi.android.search_images_and_videos_app.presentation.adapter.SearchImageAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchImagesFragment : BaseFragment(R.layout.fragment_images) {

    private val binding by viewBinding(FragmentImagesBinding::bind)
    private val viewModel by viewModels<SearchImageViewModel>()
    override fun setRecyclerView(): RecyclerView = binding.recyclerViewImages

    private val searchAdapter = SearchImageAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageAdapter = ImagePagingAdapter(
            {
                Log.d("QAZ", it.user)
                val direction =
                    SearchImagesFragmentDirections.actionPhotosFragmentToImageDetailFragment(it)
                navController.navigate(direction)
            },
            {
                viewModel.addToFavorite(it)
                showSnackbar("Image is added to your favorites")
            },
            {
                shareItem(it.user, it.pageURL)
            },
        )

        setAdapter(imageAdapter)

        binding.run {
            searchInput.requestFocus()
            searchInput.afterTextChanged(viewModel::searchImage)

            imageAdapter.withLoadStateHeaderAndFooter(
                header = LoadAdapter(imageAdapter::retry),
                footer = LoadAdapter(imageAdapter::retry)
            )

            collectWithLifecycle(viewModel.images) { data ->
                imageAdapter.submitData(data)
            }
            collectWithLifecycle(viewModel.loadImage) { state ->
                when (state) {
                    is ImagesUIState.LoadDataFromInternet -> {
                        collectWithLifecycle(viewModel.images) {
                            imageAdapter.submitData(it)
                        }
                    }
                    is ImagesUIState.GetDataWithoutInternet -> {
                        collectWithLifecycle(viewModel.getImage()) {
                            recyclerView?.adapter = searchAdapter
                            searchAdapter.submitList(it)
                        }
                        binding.searchInput.isVisible = false
                        binding.searchIcon.isVisible = false

                        binding.recyclerViewImages.layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                    }
                }
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                imageAdapter.loadStateFlow.collect { loadState ->
                    when (val refresh = loadState.mediator?.refresh) {
                        is LoadState.Loading -> {
                            textViewError.isVisible = false
                            buttonRetry.isVisible = false
                            textViewNoResults.isVisible = false

                            recyclerViewImages.isVisible {
                                !viewModel.newQueryInProgress && imageAdapter.itemCount > 0
                            }
                            viewModel.refreshInProgress = true
                            viewModel.pendingScrollToTopAfterRefresh = true
                        }

                        is LoadState.NotLoading -> {
                            textViewError.isVisible = false
                            buttonRetry.isVisible = false
                            recyclerViewImages.isVisible = imageAdapter.itemCount > 0

                            val noResult =
                                imageAdapter.itemCount < 1 && loadState.append.endOfPaginationReached
                                        && loadState.source.append.endOfPaginationReached
                            textViewNoResults.isVisible = noResult

                            viewModel.refreshInProgress = false
                            viewModel.newQueryInProgress = false
                        }

                        is LoadState.Error -> {
                            textViewNoResults.isVisible = false
                            recyclerViewImages.isVisible = imageAdapter.itemCount > 0

                            val noCachedResults =
                                imageAdapter.itemCount < 1 && loadState.source.append.endOfPaginationReached

                            textViewError.isVisible = noCachedResults
                            buttonRetry.isVisible = noCachedResults

                            val errorMessage = getString(
                                R.string.could_not_load_search_results,
                                refresh.error.localizedMessage
                                    ?: getString(R.string.unknown_error_occurred)
                            )
                            textViewError.text = errorMessage

                            if (viewModel.refreshInProgress) {
                                showSnackbar(errorMessage)
                            }
                            viewModel.refreshInProgress = false
                            viewModel.newQueryInProgress = false
                            viewModel.pendingScrollToTopAfterRefresh = false
                        }
                        else -> {}
                    }
                }
            }

            buttonRetry.setOnClickListener {
                imageAdapter.retry()
            }
        }
    }
}