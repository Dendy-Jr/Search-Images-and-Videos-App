package ui.dendi.finder.favorites_presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.extension.collectWithLifecycle
import ui.dendi.finder.favorites_presentation.adapter.FavoritesImageAdapter
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.favorites_presentation.databinding.FragmentFavoritesImageBinding
import ui.dendi.finder.favorites_presentation.viewmodel.FavoritesImageViewModel

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
            viewModel.clearAllImages()
        }
    }
}