package com.dendi.android.search_images_and_videos_app.feature_videos.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.base.BaseFragment
import com.dendi.android.search_images_and_videos_app.core.util.Constants.KEY_FILE_NAME
import com.dendi.android.search_images_and_videos_app.core.util.Constants.KEY_FILE_TYPE
import com.dendi.android.search_images_and_videos_app.core.util.Constants.KEY_FILE_URI
import com.dendi.android.search_images_and_videos_app.core.util.Constants.KEY_FILE_URL
import com.dendi.android.search_images_and_videos_app.core.util.Constants.MP4
import com.dendi.android.search_images_and_videos_app.core.managers.DownloadFileWorkManager
import com.dendi.android.search_images_and_videos_app.core.extension.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.databinding.FragmentVideoDetailsBinding
import com.dendi.android.search_images_and_videos_app.core.util.KohiiProvider
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ui.PlayerView
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.Common
import kohii.v1.core.Manager
import kohii.v1.core.MemoryMode
import kohii.v1.core.Playback
import timber.log.Timber

@AndroidEntryPoint
class VideoDetailsFragment : BaseFragment<VideoDetailsViewModel>(R.layout.fragment_video_details),
    Manager.OnSelectionListener {

    private val binding: FragmentVideoDetailsBinding by viewBinding()
    override val viewModel: VideoDetailsViewModel by viewModels()
    private val workManager by lazy { WorkManager.getInstance(requireContext()) }
    private val args by lazy { VideoDetailsFragmentArgs.fromBundle(requireArguments()) }
    override fun setRecyclerView(): RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        val video = args.video
        val kohii = KohiiProvider.get(requireContext())
        view?.let {
            kohii.register(
                this@VideoDetailsFragment,
                memoryMode = MemoryMode.BALANCED,
                activeLifecycleState = Lifecycle.State.CREATED,
            ).addBucket(it)
        }
        kohii.setUp(video.videos.large.url) {
            repeatMode = Common.REPEAT_MODE_ONE
            preload = true
        }.bind(playerView)

        userImage.loadImageOriginal(video.userImageURL)
        userName.text = video.user
        tvLikes.text = video.likes.toString()
        tvViews.text = video.views.toString()
        tvType.text = video.type
        tvTags.text = video.tags

        btnDownload.setOnClickListener {
            startDownloadingFile()
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

    private fun startDownloadingFile() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val data = Data.Builder().apply {
            putString(KEY_FILE_NAME, args.video.tags.replace(", ", "-") + ".mp4")
            putString(KEY_FILE_URL, args.video.videos.large.url)
            putString(KEY_FILE_TYPE, MP4)
        }

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(DownloadFileWorkManager::class.java)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueue(oneTimeWorkRequest)

        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(viewLifecycleOwner) { info ->
                info?.let {
                    when (it.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            Timber.d("${it.outputData.getString(KEY_FILE_URI)}")
                        }
                        WorkInfo.State.FAILED -> {
                            Timber.d("Downloading failed!")
                        }
                        WorkInfo.State.RUNNING -> {
                            Timber.d("RUNNING")
                        }
                        else -> {
                            Timber.d("Something went wrong")
                        }
                    }
                }
            }
    }
}