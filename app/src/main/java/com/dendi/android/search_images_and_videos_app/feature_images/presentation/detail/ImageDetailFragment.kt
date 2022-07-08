package com.dendi.android.search_images_and_videos_app.feature_images.presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.work.*
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.base.BaseFragment
import com.dendi.android.search_images_and_videos_app.core.util.Constants.JPG
import com.dendi.android.search_images_and_videos_app.core.util.Constants.KEY_FILE_NAME
import com.dendi.android.search_images_and_videos_app.core.util.Constants.KEY_FILE_TYPE
import com.dendi.android.search_images_and_videos_app.core.util.Constants.KEY_FILE_URI
import com.dendi.android.search_images_and_videos_app.core.util.Constants.KEY_FILE_URL
import com.dendi.android.search_images_and_videos_app.core.managers.DownloadFileWorkManager
import com.dendi.android.search_images_and_videos_app.core.extension.loadImageOriginal
import com.dendi.android.search_images_and_videos_app.databinding.FragmentImageDetailsBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class ImageDetailFragment : BaseFragment<ImageDetailViewModel>(R.layout.fragment_image_details) {
    override fun setRecyclerView(): RecyclerView? = null
    override val viewModel: ImageDetailViewModel by viewModels()
    private val binding: FragmentImageDetailsBinding by viewBinding()
    private val args by lazy { ImageDetailFragmentArgs.fromBundle(requireArguments()) }
    private val workManager by lazy { WorkManager.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        args.image.apply {
            userImage.loadImageOriginal(userImageURL)
            imageView.loadImageOriginal(largeImageURL)
            userName.text = user
            tvLikes.text = likes.toString()
            tvViews.text = views.toString()
            tvType.text = type
            tvTags.text = tags
        }

        btnDownload.setOnClickListener {
            startDownloadingFile()
        }
    }

    private fun startDownloadingFile() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresStorageNotLow(true)
            .setRequiresBatteryNotLow(true)
            .build()
        val data = Data.Builder().apply {
            putString(KEY_FILE_NAME, args.image.tags.replace(", ", "-") + ".jpg")
            putString(KEY_FILE_URL, args.image.largeImageURL)
            putString(KEY_FILE_TYPE, JPG)
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