package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
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
import com.dendi.android.search_images_and_videos_app.data.core.DownloadWorker
import com.dendi.android.search_images_and_videos_app.databinding.FragmentVideoDetailsBinding
import com.dendi.android.search_images_and_videos_app.presentation.core.KohiiProvider
import com.eazypermissions.common.model.PermissionResult
import com.eazypermissions.coroutinespermission.PermissionManager
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ui.PlayerView
import kohii.v1.core.Common
import kohii.v1.core.Manager
import kohii.v1.core.MemoryMode
import kohii.v1.core.Playback
import kotlinx.coroutines.launch


/**
 * @author Dendy-Jr on 14.12.2021
 * olehvynnytskyi@gmail.com
 */
class VideoDetailsFragment : BaseFragment(R.layout.fragment_video_details),
    Manager.OnSelectionListener {

    private val binding by viewBinding(FragmentVideoDetailsBinding::bind)

    private val args by lazy { VideoDetailsFragmentArgs.fromBundle(requireArguments()) }
    override fun setRecyclerView(): RecyclerView? = null
    private val workManager by lazy { WorkManager.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val argument = args.video
        val kohii = KohiiProvider.get(requireContext())
        kohii.register(
            this,
            memoryMode = MemoryMode.BALANCED,
            activeLifecycleState = Lifecycle.State.CREATED
        ).addBucket(view)
        kohii.setUp(argument.videos.large.url) {
            repeatMode = Common.REPEAT_MODE_ONE
            preload = true
        }.bind(binding.playerView)

        binding.run {
            userImage.loadImageOriginal(argument.userImageURL)
            userName.text = argument.user
            tvLikes.text = argument.likes.toString()
            tvViews.text = argument.views.toString()
            tvType.text = argument.type
            tvTags.text = argument.tags
        }

        binding.btnDownload.setOnClickListener {
            downloadVideo()
        }
    }

    override fun onSelection(selection: Collection<Playback>) {
        val playback = selection.firstOrNull()
        if (playback != null) {
            binding.controlView.showTimeoutMs = -1
            binding.controlView.show()
            val container = playback.container
            if (container is PlayerView) {
                container.useController = false
                binding.controlView.player = container.player
            }
            val controller = playback.config.controller
            if (controller is ControlDispatcher) {
                binding.controlView.setControlDispatcher(controller)
            }
        } else {
            binding.controlView.setControlDispatcher(null)
            binding.controlView.player = null
            binding.controlView.hide()
        }
    }

    private fun startWorker() {
        val argument = args.video
        val workRequest = OneTimeWorkRequest.Builder(DownloadWorker::class.java)
            .setInputData(
                workDataOf(
                    DownloadWorker.FILE_TYPE_PARAM to DownloadWorker.DownloadFileType.VIDEO.name,
                    DownloadWorker.FILE_URL_PARAM to argument.videos.large.url,
                    DownloadWorker.FILE_ID_PARAM to argument.id
                )
            )
            .build()

        workManager.getWorkInfoByIdLiveData(workRequest.id)
            .observe(viewLifecycleOwner, Observer { workInfo: WorkInfo? ->
                binding.run {
                    progressBar.isVisible = false
                    progressBar.progress = 0
                    if (workInfo != null && workInfo.state.isFinished) {
                        progressBar.isVisible = true
                        progressBar.progress = 100
                    }
                }
            })
        workManager.enqueue(workRequest)
    }

    private fun downloadVideo() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startWorker()
        } else {
            lifecycleScope.launch {
                val permissionReq =
                    PermissionManager.requestPermissions(
                        this@VideoDetailsFragment,
                        1234,
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