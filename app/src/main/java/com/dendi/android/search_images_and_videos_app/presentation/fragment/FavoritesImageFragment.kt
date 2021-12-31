package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.core.showSnackbar
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesImageBinding
import com.dendi.android.search_images_and_videos_app.presentation.favorite.FavoritesImageAdapter
import com.dendi.android.search_images_and_videos_app.presentation.favorite.FavoritesImageViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Dendy-Jr on 29.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesImageFragment : BaseFragment(R.layout.fragment_favorites_image) {

    private val binding by viewBinding(FragmentFavoritesImageBinding::bind)
    override fun setRecyclerView() = binding.rvImagesFavorite

    private val viewModel by viewModel<FavoritesImageViewModel>()
    private val imageAdapter = FavoritesImageAdapter(
        object : OnClickListener<ImageEntity> {
            override fun click(item: ImageEntity) {
                viewModel.deleteFromFavoritesImage(item)
                showSnackbar("Image is deleted from your favorites")
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter(imageAdapter)

        collectLatestLifecycleFlow(viewModel.favoritesImage) {
            imageAdapter.submitList(it)
        }
    }
}