package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.extension.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.databinding.FragmentVideoDetailsBinding
import com.dendi.android.search_images_and_videos_app.presentation.core.KohiiProvider
import com.google.android.exoplayer2.ControlDispatcher
import com.google.android.exoplayer2.ui.PlayerView
import dagger.hilt.android.AndroidEntryPoint
import kohii.v1.core.Common
import kohii.v1.core.Manager
import kohii.v1.core.MemoryMode
import kohii.v1.core.Playback

@AndroidEntryPoint
class VideoDetailsFragment : BaseFragment(R.layout.fragment_video_details),
    Manager.OnSelectionListener {

    private val binding by viewBinding(FragmentVideoDetailsBinding::bind)

    private val args by lazy { VideoDetailsFragmentArgs.fromBundle(requireArguments()) }
    override fun setRecyclerView(): RecyclerView? = null

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
}