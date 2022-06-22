package com.dendi.android.search_images_and_videos_app.core

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.lifecycle.ViewModel
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.extension.showToast
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesImageViewModel
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesVideoViewModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

interface DialogManager {

    fun showDeleteDialog(viewModel: ViewModel)
}

class DialogManagerImpl @Inject constructor(
    private val context: Context,
) : DialogManager {
    override fun showDeleteDialog(viewModel: ViewModel) {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    when (viewModel) {
                        is FavoritesVideoViewModel -> {
                            viewModel.deleteAllFavoritesVideos()
                            context.showToast(R.string.favorites_removed)
                        }
                        is FavoritesImageViewModel -> {
                            viewModel.deleteAllFavoritesImages()
                            context.showToast(R.string.favorites_removed)
                        }
                    }
                }

                DialogInterface.BUTTON_NEGATIVE -> {
                    context.showToast(R.string.favorites_not_deleted)
                }
            }
        }

        val dialog = AlertDialog.Builder(context)
            .setCancelable(true)
            .setIcon(R.mipmap.ic_launcher_round)
            .setTitle(R.string.delete_all_favorites)
            .setMessage(R.string.remove_all_of_your_favorites)
            .setPositiveButton("Yes", listener)
            .setNegativeButton("No", listener)
            .setOnCancelListener {
                context.showToast(R.string.dialog_cancelled)
            }
            .setOnDismissListener {
                Log.d("Dialog", "Dialog dismissed")
            }
            .create()
        dialog.show()
    }
}

@Module
@InstallIn(SingletonComponent::class)
interface DialogManagerModule {

    @Binds
    @Singleton
    fun bind(impl: DialogManagerImpl): DialogManager
}