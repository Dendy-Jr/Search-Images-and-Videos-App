package ui.dendi.finder.favorites_presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.extension.collectWithLifecycle
import ui.dendi.finder.core.core.extension.parentViewModel
import ui.dendi.finder.core.core.multichoice.ImageListItem
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.favorites_presentation.adapter.FavoritesImageAdapter
import ui.dendi.finder.favorites_presentation.adapter.ImageAdapterListener
import ui.dendi.finder.favorites_presentation.databinding.FragmentFavoritesImageBinding
import ui.dendi.finder.favorites_presentation.viewmodel.FavoritesImageViewModel

@AndroidEntryPoint
class FavoritesImageFragment :
    BaseFragment<FavoritesImageViewModel>(R.layout.fragment_favorites_image) {

    private val binding: FragmentFavoritesImageBinding by viewBinding()
    override val viewModel: FavoritesImageViewModel by parentViewModel()
    private val imageAdapter = FavoritesImageAdapter(
        object : ImageAdapterListener {
            override fun onImageDelete(image: ImageListItem) {
                viewModel.deleteFromFavoritesImage(image)
            }

            override fun onImageChosen(image: ImageListItem) {
                // TODO navigate to details screen
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
        collectWithLifecycle(viewModel.favoriteImagesState) { state ->
            imageAdapter.submitList(state.images)

            selectOrClearAllTextView.isVisible = state.images.isNotEmpty()
            selectionStateTextView.isVisible = state.images.isNotEmpty()

            selectOrClearAllTextView.setText(state.selectAllOperation.titleRes)
            selectionStateTextView.text = getString(
                R.string.selection_state,
                state.totalCheckedCount, state.totalCount
            )
        }

        collectWithLifecycle(viewModel.needShowDeleteButton) { needShowButton ->
            btnDeleteAll.isVisible = needShowButton
        }

        selectOrClearAllTextView.setOnClickListener {
            viewModel.selectOrClearAll()
        }

        favoritesRecyclerView.adapter = imageAdapter
        btnDeleteAll.setOnClickListener {
            viewModel.clearAllImages()
        }
    }
}