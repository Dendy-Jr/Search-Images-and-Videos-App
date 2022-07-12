package com.dendi.android.search_images_and_videos_app.feature_images.domain.usecase

import com.dendi.android.search_images_and_videos_app.core.DownloadManager
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