package com.dendi.android.search_images_and_videos_app.feature_favorites.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.base.BaseFragment
import com.dendi.android.search_images_and_videos_app.core.extension.collectWithLifecycle
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesImageBinding
import com.dendi.android.search_images_and_videos_app.feature_favorites.presentation.adapter.FavoritesImageAdapter
import com.dendi.android.search_images_and_videos_app.feature_favorites.presentation.viewmodel.FavoritesImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesImageFragment :
    BaseFragment<FavoritesImageViewModel>(R.layout.fragment_favorites_image) {

    private val binding: FragmentFavoritesImageBinding by viewBinding()
    override val viewModel: FavoritesImageViewModel by viewModels()
    private val imageAdapter = FavoritesImageAdapter {
        viewModel.deleteFromFavoritesImage(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        collectWithLifecycle(viewModel.favoriteImages) {
            imageAdapter.submitList(it)
        }

        collectWithLifecycle(viewModel.needShowDeleteButton) { needShowButton ->
            btnDeleteAll.isVisible = needShowButton
        }

        favoritesRecyclerView.adapter = imageAdapter
        btnDeleteAll.setOnClickListener {
            viewModel.deleteAllImages()
        }
    }
}