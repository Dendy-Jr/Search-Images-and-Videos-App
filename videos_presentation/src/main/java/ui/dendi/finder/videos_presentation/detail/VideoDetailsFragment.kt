package ui.dendi.finder.videos_presentation.detail

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.CustomPopupMenu
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.work.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ui.PlayerView
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.Common
import kohii.v1.core.Manager
import kohii.v1.core.MemoryMode
import kohii.v1.core.Playback
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.extension.dateConverter
import ui.dendi.finder.core.core.extension.showToast
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.KEY_FILE_NAME
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.KEY_FILE_TYPE
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.KEY_FILE_URI
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.KEY_FILE_URL
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.MP4
import ui.dendi.finder.core.core.util.KohiiProvider
import ui.dendi.finder.videos_presentation.R
import ui.dendi.finder.videos_presentation.databinding.FragmentVideoDetailsBinding
import java.time.LocalDateTime

@AndroidEntryPoint
class VideoDetailsFragment : BaseFragment<VideoDetailsViewModel>(R.layout.fragment_video_details),
    Manager.OnSelectionListener {

    private val binding: FragmentVideoDetailsBinding by viewBinding()
    override val viewModel: VideoDetailsViewModel by viewModels()

    private val workManager by lazy { WorkManager.getInstance(requireContext()) }
    private val args: VideoDetailsFragmentArgs by navArgs()
    private var videoQuality = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.qualityFLow.collectLatest { quality ->
                val kohii = KohiiProvider.get(requireContext())
                view?.let {
                    kohii.register(
                        this@VideoDetailsFragment,
                        memoryMode = MemoryMode.BALANCED,
                        activeLifecycleState = Lifecycle.State.RESUMED,
                    ).addBucket(it)
                }
                kohii.setUp(quality) {
                    tag = quality
                    repeatMode = Common.REPEAT_MODE_ONE
                    preload = true
                }.bind(playerView)
                videoQuality = quality
            }
        }
        args.apply {
            toolbar.setTitle(video.user)
            toolbar.setUserImage(video.userImageURL)
            tvLikes.text = video.likes.toString()
            tvTags.text = video.tags
            tvType.text = video.type
            tvViews.text = video.views.toString()
        }

        btnDownload.setOnClickListener {
            startDownloadingFile()
        }

        btnQuality.setOnClickListener {
            showPopup(it)
        }

        ivSaveToFavorites.setOnClickListener {
            viewModel.saveVideoToFavorites()
            requireContext().showToast(R.string.added_to_favorite)
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

    private fun showPopup(view: View) {
        val popupMenu = CustomPopupMenu(view.context, view)

        popupMenu.menu.add(0, 0, Menu.NONE, getString(R.string.large)).apply {
            //TODO
            setIcon(R.drawable.ic_video_quality_4k)
        }
        popupMenu.menu.add(0, 1, Menu.NONE, getString(R.string.medium)).apply {
            setIcon(R.drawable.ic_video_quality_hd)
        }
        popupMenu.menu.add(0, 2, Menu.NONE, getString(R.string.small)).apply {
            setIcon(R.drawable.ic_video_quality_hq)
        }
        popupMenu.menu.add(0, 3, Menu.NONE, getString(R.string.tiny)).apply {
            setIcon(R.drawable.ic_video_quality_sd)
        }

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                0 -> viewModel.setVideoQuality(VideoQuality.LARGE)
                1 -> viewModel.setVideoQuality(VideoQuality.MEDIUM)
                2 -> viewModel.setVideoQuality(VideoQuality.SMALL)
                3 -> viewModel.setVideoQuality(VideoQuality.TINY)
            }
            return@setOnMenuItemClickListener true
        }
        popupMenu.show()
    }

    private fun startDownloadingFile() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val data = Data.Builder().apply {
            putString(KEY_FILE_NAME, setVideoName())
            putString(KEY_FILE_URL, videoQuality)
            putString(KEY_FILE_TYPE, MP4)
        }
        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(DownloadFileWorkManager::class.java)
            .setConstraints(constraints)
            .setInputData(data.build())
            .build()

        workManager.enqueue(oneTimeWorkRequest)
        workManager.getWorkInfoByIdLiveData(oneTimeWorkRequest.id)
            .observe(viewLifecycleOwner) { info ->
                info?.let { workInfo ->
                    when (workInfo.state) {
                        WorkInfo.State.SUCCEEDED -> {
                            requireContext().showToast(R.string.video_uploaded_successfully)
                            log("${workInfo.outputData.getString(KEY_FILE_URI)}")
                        }
                        WorkInfo.State.FAILED -> {
                            requireContext().showToast(R.string.downloading_failed)
                            log(getString(R.string.downloading_failed))
                        }
                        WorkInfo.State.RUNNING -> {
                            requireContext().showToast(R.string.download_started)
                            log(getString(R.string.download_started))
                        }
                        else -> {
                            log(getString(R.string.something_went_wrong))
                        }
                    }
                }
            }
    }

    private fun setVideoName(): String {
        val firstTag = args.video.tags.replaceAfter(",", "").removeSuffix(",")
        val currentDate = LocalDateTime.now().toString().dateConverter()
        val editedUrl = videoQuality.replaceBefore("width", "")
            .replaceAfter("&", "-").replace("&", "")
        val suffix = ".mp4"
        return "$firstTag-$editedUrl$currentDate$suffix"
    }
}