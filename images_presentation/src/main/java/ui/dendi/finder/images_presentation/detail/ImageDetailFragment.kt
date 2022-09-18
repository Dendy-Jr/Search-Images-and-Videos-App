package ui.dendi.finder.images_presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.work.*
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.extension.loadImageOriginal
import ui.dendi.finder.core.core.extension.showToast
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager
import ui.dendi.finder.core.core.util.Constants.JPG
import ui.dendi.finder.core.core.util.Constants.KEY_FILE_NAME
import ui.dendi.finder.core.core.util.Constants.KEY_FILE_TYPE
import ui.dendi.finder.core.core.util.Constants.KEY_FILE_URI
import ui.dendi.finder.core.core.util.Constants.KEY_FILE_URL
import ui.dendi.finder.images_presentation.R
import ui.dendi.finder.images_presentation.databinding.FragmentImageDetailsBinding

@AndroidEntryPoint
class ImageDetailFragment : BaseFragment<ImageDetailViewModel>(R.layout.fragment_image_details) {
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
            imageView.loadImageOriginal(largeImageURL)
            tvLikes.text = likes.toString()
            tvViews.text = views.toString()
            tvType.text = type
            tvTags.text = tags
            toolbar.setTitle(user)
            toolbar.setUserImage(userImageURL)
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
                            requireContext().showToast(R.string.image_uploaded_successfully)
                            Timber.d("${it.outputData.getString(KEY_FILE_URI)}")
                        }
                        WorkInfo.State.FAILED -> {
                            requireContext().showToast(R.string.downloading_failed)
                            Timber.d(getString(R.string.downloading_failed))
                        }
                        WorkInfo.State.RUNNING -> {
                            Timber.d(getString(R.string.download_started))
                        }
                        else -> {
                            Timber.d(getString(R.string.something_went_wrong))
                        }
                    }
                }
            }
    }
}