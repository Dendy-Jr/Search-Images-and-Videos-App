package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.DialogManagerImpl
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesImageBinding
import com.dendi.android.search_images_and_videos_app.presentation.adapter.FavoritesImageAdapter
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesImageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesImageFragment : BaseFragment(R.layout.fragment_favorites_image) {

    private val binding by viewBinding(FragmentFavoritesImageBinding::bind)
    override fun setRecyclerView() = binding.rvImagesFavorite
    private val dialog by lazy { DialogManagerImpl(requireContext()) }

    private val viewModel by viewModels<FavoritesImageViewModel>()
    private val imageAdapter = FavoritesImageAdapter {
        viewModel.deleteFromFavoritesImage(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAdapter(imageAdapter)

        collectWithLifecycle(viewModel.favoritesImage) {
            imageAdapter.submitList(it)
        }

        binding.btnDeleteAll.setOnClickListener {
            dialog.showDeleteDialog(viewModel)

        }
    }
}