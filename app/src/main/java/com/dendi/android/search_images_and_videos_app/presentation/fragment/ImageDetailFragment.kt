package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.data.image.cloud.DownloadWorker
import com.dendi.android.search_images_and_videos_app.data.image.cloud.DownloadWorker.Companion.Progress
import com.dendi.android.search_images_and_videos_app.databinding.FragmentImageDetailsBinding
import com.dendi.android.search_images_and_videos_app.presentation.image.ImageDetailViewModel
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.coroutinespermission.PermissionManager
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * @author Dendy-Jr on 22.12.2021
 * olehvynnytskyi@gmail.com
 */
class ImageDetailFragment : BaseFragment(R.layout.fragment_image_details) {
    override fun setRecyclerView(): RecyclerView? = null
    private val binding by viewBinding(FragmentImageDetailsBinding::bind)
    private val viewModel by viewModel<ImageDetailViewModel>()
    private val args by lazy { ImageDetailFragmentArgs.fromBundle(requireArguments()) }

    private val workManager by lazy { WorkManager.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val argument = args.image
        binding.run {
            userImage.loadImageOriginal(argument.userImageURL)
            imageView.loadImageOriginal(argument.largeImageURL)
            userName.text = argument.user
            tvLikes.text = argument.likes.toString()
            tvViews.text = argument.views.toString()
            tvType.text = argument.type
            tvTags.text = argument.tags
        }

        binding.btnDownload.setOnClickListener {
            downloadImage()
        }
    }

    private fun startWorker() {
        val workRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            .setInputData(
                workDataOf(
                    DownloadWorker.FILE_TYPE_PARAM to DownloadWorker.DownloadFileType.IMAGE.name,
                    DownloadWorker.FILE_URL_PARAM to args.image.largeImageURL,
                    DownloadWorker.FILE_ID_PARAM to args.image.id
                )
            )
            .build()
        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(viewLifecycleOwner, Observer { workInfo: WorkInfo? ->

                if (workInfo != null) {
                    val progress = workInfo.progress
                    val value = progress.getInt(DownloadWorker.UPLOAD_CHANNEL_ID, 0)
                    binding.tvProgressPercent.text = value.toString()

                    Log.d("progress", value.toString())
                }
            })
        workManager.enqueue(workRequest)
    }

    private fun downloadImage() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startWorker()
        } else {
            lifecycleScope.launch {
                val permissionReq =
                    PermissionManager.requestPermissions(
                        this@ImageDetailFragment,
                        123,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )

                when (permissionReq) {
                    is PermissionResult.PermissionGranted -> {
                        startWorker()
                    }
                }
            }
        }
    }
}