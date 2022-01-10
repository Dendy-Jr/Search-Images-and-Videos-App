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
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesVideoBinding
import com.dendi.android.search_images_and_videos_app.presentation.core.KohiiProvider
import com.dendi.android.search_images_and_videos_app.presentation.adapter.FavoritesVideoAdapter
import com.dendi.android.search_images_and_videos_app.presentation.viewmodel.FavoritesVideoViewModel
import kohii.v1.core.MemoryMode
import kohii.v1.core.Strategy
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * @author Dendy-Jr on 29.12.2021
 * olehvynnytskyi@gmail.com
 */
class FavoritesVideoFragment : BaseFragment(R.layout.fragment_favorites_video) {

    private val binding by viewBinding(FragmentFavoritesVideoBinding::bind)
    override fun setRecyclerView() = binding.rvVideosFavorite
    private val viewModel by viewModel<FavoritesVideoViewModel>()

    private val dialog by lazy { ShowDialog.ShowDialogImpl(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val kohii = KohiiProvider.get(requireContext())
        kohii.register(this, memoryMode = MemoryMode.BALANCED)
            .addBucket(
                view = binding.rvVideosFavorite,
                strategy = Strategy.MULTI_PLAYER,
                selector = { candidates ->
                    candidates.take(3)
                }
            )

        val videoAdapter = FavoritesVideoAdapter(kohii, object : OnClickListener<VideoCache> {
            override fun click(item: VideoCache) {
                viewModel.deleteFromFavoritesVideo(item)
                showSnackbar("Video is deleted from your favorites")
            }
        })
        setAdapter(videoAdapter)

        collectLatestLifecycleFlow(viewModel.favoritesVideo) {
            videoAdapter.submitList(it)
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
                    viewModel.deleteAllFavoritesVideos()
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