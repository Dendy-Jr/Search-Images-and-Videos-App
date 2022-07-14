package ui.dendi.android.finder.feature_favorites.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.android.finder.R
import ui.dendi.android.finder.core.base.BaseFragment
import ui.dendi.android.finder.core.extension.collectWithLifecycle
import ui.dendi.android.finder.databinding.FragmentFavoritesImageBinding
import ui.dendi.android.finder.feature_favorites.presentation.adapter.FavoritesImageAdapter
import ui.dendi.android.finder.feature_favorites.presentation.viewmodel.FavoritesImageViewModel

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