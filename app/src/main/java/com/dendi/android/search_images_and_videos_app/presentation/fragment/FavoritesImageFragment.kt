package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.OnClickListener
import com.dendi.android.search_images_and_videos_app.core.ShowDialog
import com.dendi.android.search_images_and_videos_app.core.showSnackbar
import com.dendi.android.search_images_and_videos_app.core.showToast
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesImageBinding
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.presentation.adapter.FavoritesImageAdapter
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesImageViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Dendy-Jr on 29.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesImageFragment : BaseFragment(R.layout.fragment_favorites_image) {

    private val binding by viewBinding(FragmentFavoritesImageBinding::bind)
    override fun setRecyclerView() = binding.rvImagesFavorite
    private val dialog by lazy { ShowDialog.ShowDialogImpl(requireContext()) }

    private val viewModel by viewModel<FavoritesImageViewModel>()
    private val imageAdapter = FavoritesImageAdapter(
        object : OnClickListener<Image> {
            override fun click(item: Image) {
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

        binding.btnDeleteAll.setOnClickListener {
//            showDialog(it)
            dialog.showAlertDialog(viewModel)

        }
    }

    private fun showDialog(view: View) {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    viewModel.deleteAllFavoritesImages()
                    showToast(getString(R.string.favorites_removed), requireContext())

                }
                DialogInterface.BUTTON_NEGATIVE -> {
                    showToast(getString(R.string.favorites_not_deleted), requireContext())
                }
            }
        }
        val dialog = AlertDialog.Builder(context)
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(getString(R.string.delete_all_favorites))
            .setMessage(getString(R.string.remove_all_of_your_favorites))
            .setPositiveButton(getString(R.string.yes), listener)
            .setNegativeButton(getString(R.string.no), listener)
            .setOnCancelListener {
                showToast(getString(R.string.dialog_cancelled), requireContext())
            }
            .setOnDismissListener {
                Log.d("TAG", getString(R.string.dialog_dismissed))
            }
            .create()
        dialog.show()
    }
}