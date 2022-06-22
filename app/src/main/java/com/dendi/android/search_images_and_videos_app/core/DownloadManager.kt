package com.dendi.android.search_images_and_videos_app.core

import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DownloadManager @Inject constructor(
    private val api: DownloadFileApi,
) {

    suspend fun download(url: String, file: File) {
        api.downloadFile(url).byteStream().use {
            file.parentFile?.mkdirs()

            FileOutputStream(file).use { targetOutputStream ->
                it.copyTo(targetOutputStream)
            }
        }
    }
}