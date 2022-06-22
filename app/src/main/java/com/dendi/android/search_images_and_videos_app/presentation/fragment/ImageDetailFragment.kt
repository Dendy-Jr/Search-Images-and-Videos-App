package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.extension.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.databinding.FragmentImageDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ImageDetailFragment : BaseFragment(R.layout.fragment_image_details) {
    override fun setRecyclerView(): RecyclerView? = null
    private val binding by viewBinding(FragmentImageDetailsBinding::bind)
    private val args by lazy { ImageDetailFragmentArgs.fromBundle(requireArguments()) }


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
    }
}