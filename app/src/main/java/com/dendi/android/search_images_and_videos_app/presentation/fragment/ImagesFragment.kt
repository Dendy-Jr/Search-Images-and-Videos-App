package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.*
import com.dendi.android.search_images_and_videos_app.databinding.FragmentImagesBinding
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.presentation.adapter.ImagePagingAdapter
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.ImageViewModel
import com.dendi.android.search_images_and_videos_app.presentation.adapter.LoadAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Dendy-Jr on 11.12.2021
 * olehvynnytskyi@gmail.com
 */
@ExperimentalPagingApi
@ExperimentalCoroutinesApi
class ImagesFragment : BaseFragment(R.layout.fragment_images) {

    private val binding by viewBinding(FragmentImagesBinding::bind)
    private val viewModel by viewModel<ImageViewModel>()
    override fun setRecyclerView(): RecyclerView = binding.recyclerViewImages

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageAdapter = ImagePagingAdapter(object : OnClickListener<Image> {
            override fun click(item: Image) {
                Log.d("QAZ", item.user)
                val direction =
                    ImagesFragmentDirections.actionPhotosFragmentToImageDetailFragment(item)
                navController.navigate(direction)
            }
        },
            object : OnClickListener<Image> {
                override fun click(item: Image) {
                    viewModel.addToFavorite(item)
                    showSnackbar("Image is added to your favorites")
                }
            },
            object : OnClickListener<Image> {
                override fun click(item: Image) {
                    shareItem(item.user, item.pageURL)
                }
            }
        )

        setAdapter(imageAdapter)

        binding.run {
            searchInput.requestFocus()
            searchInput.afterTextChanged(viewModel::searchImage)

            imageAdapter.withLoadStateHeaderAndFooter(
                header = LoadAdapter(imageAdapter::retry),
                footer = LoadAdapter(imageAdapter::retry)
            )

            collectLatestLifecycleFlow(viewModel.images) { data ->
                imageAdapter.submitData(data)
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                imageAdapter.loadStateFlow.collect { loadState ->
                    when (val refresh = loadState.mediator?.refresh) {
                        is LoadState.Loading -> {
                            textViewError.isVisible = false
                            buttonRetry.isVisible = false
                            textViewNoResults.isVisible = false

                            recyclerViewImages.showIfOrInvisible {
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
                    }
                }
            }

            buttonRetry.setOnClickListener {
                imageAdapter.retry()
            }
        }
    }
}