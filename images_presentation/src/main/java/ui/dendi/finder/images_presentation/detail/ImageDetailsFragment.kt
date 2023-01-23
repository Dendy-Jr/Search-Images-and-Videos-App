package ui.dendi.finder.images_presentation.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.work.*
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.extension.loadImage
import ui.dendi.finder.core.core.extension.showToast
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.JPG
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.KEY_FILE_NAME
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.KEY_FILE_TYPE
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.KEY_FILE_URI
import ui.dendi.finder.core.core.managers.DownloadFileWorkManager.Companion.KEY_FILE_URL
import ui.dendi.finder.images_presentation.R
import ui.dendi.finder.images_presentation.databinding.FragmentImageDetailsBinding

@AndroidEntryPoint
class ImageDetailsFragment : BaseFragment<ImageDetailsViewModel>(R.layout.fragment_image_details) {
    override val viewModel: ImageDetailsViewModel by viewModels()
    private val binding: FragmentImageDetailsBinding by viewBinding()
    private val args: ImageDetailsFragmentArgs by navArgs()
    private val workManager by lazy { WorkManager.getInstance(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        args.image.apply {
            imageView.loadImage(largeImageURL)

            toolbar.setTitle(user)
            toolbar.setUserImage(userImageURL)
            tvLikes.text = likes.toString()
            tvTags.text = tags
            tvType.text = type
            tvViews.text = views.toString()
        }

        btnDownload.setOnClickListener {
            startDownloadingFile()
        }

        ivSaveToFavorites.setOnClickListener {
            viewModel.saveImageToFavorites()
            requireContext().showToast(R.string.added_to_favorite)
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
                            log("${it.outputData.getString(KEY_FILE_URI)}")
                        }
                        WorkInfo.State.FAILED -> {
                            requireContext().showToast(R.string.downloading_failed)
                            log(getString(R.string.downloading_failed))
                        }
                        WorkInfo.State.RUNNING -> {
                            log(getString(R.string.download_started))
                        }
                        else -> {
                            log(getString(R.string.something_went_wrong))
                        }
                    }
                }
            }
    }
}