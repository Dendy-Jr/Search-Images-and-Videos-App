package com.dendi.android.search_images_and_videos_app.core

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.lifecycle.ViewModel
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesImageViewModel
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesVideoViewModel

/**
 * @author Dendy-Jr on 03.01.2022
 * olehvynnytskyi@gmail.com
 */
interface ShowDialog {

    fun showAlertDialog(viewModel: ViewModel)

    class ShowDialogImpl(
        private val context: Context
    ) : ShowDialog {
        override fun showAlertDialog(viewModel: ViewModel) {
            val listener = DialogInterface.OnClickListener { _, which ->
                when (which) {
                    DialogInterface.BUTTON_POSITIVE -> {
                        when (viewModel) {
                            is FavoritesVideoViewModel -> {
                                viewModel.deleteAllFavoritesVideos()
                                showToast("Favorites are removed", context)
                            }
                            is FavoritesImageViewModel -> {
                                viewModel.deleteAllFavoritesImages()
                                showToast("Favorites are removed", context)

                            }
                        }
                    }

                    DialogInterface.BUTTON_NEGATIVE -> {
                        showToast("Favorites are not deleted", context)
                    }
                }
            }

            val dialog = AlertDialog.Builder(context)
                .setCancelable(true)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle("Delete all favorites")
                .setMessage("Are you sure you want to remove all of your favorites?")
                .setPositiveButton("Yes", listener)
                .setNegativeButton("No", listener)
                .setOnCancelListener {
                    showToast("Dialog cancelled", context)
                }
                .setOnDismissListener {
                    Log.d("TAG", "Dialog dismissed")
                }
                .create()

            dialog.show()
        }
    }
}