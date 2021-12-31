package com.dendi.android.search_images_and_videos_app.data.image.cloud

/**
 * @author Dendy-Jr on 30.12.2021
 * olehvynnytskyi@gmail.com
 */
class FileDownloadProgressEvent(val fileId: Long, val progress: Int)
class FileDownloadStarted(val fileId: Long)
class FileDownloadEnded(val fileId: Long)