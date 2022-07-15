package ui.dendi.finder.app.feature_images.domain.usecase

import ui.dendi.finder.app.core.DownloadManager
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadFileUseCase @Inject constructor(
    private val downloadManager: DownloadManager,
) {

    suspend operator fun invoke(url: String, fileName: String): Result<Unit> {
        return downloadManager.download(url = url, fileName = fileName)
    }
}