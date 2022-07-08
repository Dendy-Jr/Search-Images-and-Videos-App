package com.dendi.android.search_images_and_videos_app.feature_images.presentation.images

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.base.BaseFragment
import com.dendi.android.search_images_and_videos_app.core.extension.collectWithLifecycle
import com.dendi.android.search_images_and_videos_app.core.extension.showToast
import com.dendi.android.search_images_and_videos_app.core.managers.ConnectionLiveDataManager
import com.dendi.android.search_images_and_videos_app.databinding.FragmentImagesBinding
import com.dendi.android.search_images_and_videos_app.presentation.adapter.LocalImagesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchImagesFragment : BaseFragment<SearchImagesViewModel>(R.layout.fragment_images) {

    private val binding: FragmentImagesBinding by viewBinding()
    override val viewModel: SearchImagesViewModel by viewModels()
    override fun setRecyclerView(): RecyclerView = binding.recyclerViewImages

    @Inject
    lateinit var connectionLiveDataManager: ConnectionLiveDataManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        val pagingAdapter = SearchImagesPagingAdapter(
            {
                viewModel.launchDetailScreen(it)
            },
            {
                viewModel.addToFavorite(it)
                requireContext().showToast("Added to favorites")
            },
            {
                shareItem(it.user, it.pageURL)
            },
        )

        val localAdapter = LocalImagesAdapter()

        searchEditText.setSearchTextChangedClickListener {
            viewModel.setSearchBy(it)
        }

//        setAdapter(pagingAdapter)
//        collectWithLifecycle(viewModel.imagesFlow) {
//            pagingAdapter.submitData(it)
//        }

        connectionLiveDataManager = ConnectionLiveDataManager(requireContext())
        connectionLiveDataManager.observe(viewLifecycleOwner) { isNetworkAvailable ->
            searchEditText.isVisible = isNetworkAvailable
            if (isNetworkAvailable) {
                setAdapter(pagingAdapter)
                collectWithLifecycle(viewModel.imagesFlow) {
                    pagingAdapter.submitData(it)
                }
            } else {
                setAdapter(localAdapter)
                collectWithLifecycle(viewModel.localImages) {
                    localAdapter.submitList(it)
                }
            }
        }

//        collectWithLifecycle(viewModel.scrollList) {
//            recyclerView?.scrollToPosition(0)
//        }
    }
}