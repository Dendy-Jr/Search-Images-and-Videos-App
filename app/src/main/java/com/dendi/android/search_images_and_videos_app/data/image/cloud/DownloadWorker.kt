package com.dendi.android.search_images_and_videos_app.data.image.cloud

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.media.MediaScannerConnection
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.net.toUri
import androidx.work.*
import com.dendi.android.search_images_and_videos_app.R
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.FileOutputStream

/**
 * @author Dendy-Jr on 30.12.2021
 * olehvynnytskyi@gmail.com
 */
class DownloadWorker constructor(
    appContext: Context,
    params: WorkerParameters,
    private val api: FileApi
) : CoroutineWorker(appContext, params) {

    private val notificationManager = NotificationManagerCompat.from(appContext)
    private val cancelIntent =
        WorkManager.getInstance(applicationContext).createCancelPendingIntent(id)
    private val notificationBuilder =
        NotificationCompat.Builder(appContext, UPLOAD_CHANNEL_ID).apply {
            createChannel()
            setContentTitle(appContext.getString(R.string.file_download_title))
            setContentText(appContext.getString(R.string.file_download_progress))
            setSmallIcon(R.mipmap.ic_launcher)
            addAction(
                android.R.drawable.ic_delete,
                appContext.getString(R.string.file_download_cancel),
                cancelIntent
            )
            priority = NotificationCompat.PRIORITY_DEFAULT
        }
    private val fileType: String =
        inputData.getString(FILE_TYPE_PARAM) ?: DownloadFileType.IMAGE.name
    private val fileId: Long = inputData.getLong(FILE_ID_PARAM, 0)
    private val fileUrl: String = inputData.getString(FILE_URL_PARAM) ?: ""
    private val notificationId = fileUrl.hashCode()

    @RequiresApi(Build.VERSION_CODES.M)
    override suspend fun doWork(): Result {
        if (fileUrl.isEmpty()) {
            return errorLoadFile("error")
        }
        addFileIdToSet(fileId)

        setForeground(ForegroundInfo(notificationId, notificationBuilder.build()))
        EventBus.getDefault().post(FileDownloadStarted(fileId))

        val response = runCatching { api.downloadFile(fileUrl) }.getOrNull()
            ?: return errorLoadFile(getString(R.string.file_download_connection_error))

        val savedFilePath: String
        val outputStream = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver = applicationContext.contentResolver
            val contentValues = ContentValues().apply {
                put(
                    MediaStore.MediaColumns.DISPLAY_NAME,
                    "${fileNamePrefix}${fileId}"
                )
                put(
                    MediaStore.MediaColumns.MIME_TYPE,
                    "${response.contentType()?.type}/${response.contentType()?.subtype}"
                )
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM)
            }

            val uri = if (fileType == DownloadFileType.IMAGE.name) {
                resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            } else {
                resolver.insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
            } ?: return errorLoadFile(getString(R.string.file_local_create_error))

            savedFilePath = uri.toString()

            runCatching { resolver.openOutputStream(uri) }.getOrNull()
                ?: return errorLoadFile(getString(R.string.file_local_create_error))
        } else {
            val rootFile = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                    .toString() + "/Camera/"
            )
            if (!rootFile.exists()) rootFile.mkdirs()

            val file = runCatching {
                File.createTempFile(
                    "${fileNamePrefix}${fileId}",
                    ".${response.contentType()?.subtype}",
                    rootFile
                )
            }.getOrNull() ?: return errorLoadFile(getString(R.string.file_local_create_error))

            savedFilePath = file.toString()

            runCatching { FileOutputStream(file) }.getOrNull()
                ?: return errorLoadFile(getString(R.string.file_local_create_error))
        }

        response.byteStream().use { inputStream ->
            outputStream.use { outputStream ->
                val contentLength = response.contentLength()
                val byteArray = ByteArray(8192)
                var progressBytes = 0L
                var lastUpdateNotification = 0L

                while (true) {
                    val bytes = runCatching { inputStream.read(byteArray) }.getOrNull() ?: -1

                    if (bytes == -1) break

                    runCatching { outputStream.write(byteArray, 0, bytes) }
                        .onFailure {
                            return errorLoadFile(getString(R.string.file_decode_error))
                        }

                    progressBytes += bytes

                    if (System.currentTimeMillis() - lastUpdateNotification > 1000) {
                        showProgress(((progressBytes * 100) / contentLength).toInt(), fileId)
                        lastUpdateNotification = System.currentTimeMillis()
                    }
                }
            }
        }

        MediaScannerConnection.scanFile(
            applicationContext, arrayOf(savedFilePath), null
        ) { path, _ ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(
                path.toUri(),
                "${response.contentType()?.type}/${response.contentType()?.subtype}"
            )
            val pendingIntent = PendingIntent.getActivity(
                applicationContext,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )
//            val imageBitmap =
//                Glide.with(applicationContext).asBitmap().load(path.toUri()).submit().get()
            val finishedDownload =
                NotificationCompat.Builder(applicationContext, UPLOAD_CHANNEL_ID).apply {
                    setContentTitle(applicationContext.getString(R.string.file_download_title))
                    setContentText(applicationContext.getString(R.string.file_download_finish))
                    setSmallIcon(R.mipmap.ic_launcher)
//                    setLargeIcon(imageBitmap)
                    setContentIntent(pendingIntent)
                }
            notificationManager.notify(notificationId, finishedDownload.build())
        }

//        val firstUpdate = workDataOf(Progress to 0)
//        val lastUpdate = workDataOf(Progress to 100)
//        setProgress(firstUpdate)
//        delay(delayDuration)
//        setProgress(lastUpdate)

        removeFileIdFromSet(fileId)
        EventBus.getDefault().post(FileDownloadEnded(fileId))
        return Result.success()
    }

    private fun showProgress(progress: Int, fileId: Long) {
        notificationBuilder.setProgress(100, progress, false)
        notificationManager.notify(notificationId, notificationBuilder.build())
        EventBus.getDefault().post(FileDownloadProgressEvent(fileId, progress))
    }

    private fun createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mNotificationManager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val mChannel = NotificationChannel(
                UPLOAD_CHANNEL_ID,
                applicationContext.getString(R.string.image_download_channel_name),
                importance
            )
            mNotificationManager.createNotificationChannel(mChannel)
        }
    }

    private fun errorLoadFile(errorDetails: String): Result {
        removeFileIdFromSet(inputData.getLong(FILE_ID_PARAM, 0))
        val errorDownload =
            NotificationCompat.Builder(applicationContext, UPLOAD_CHANNEL_ID).apply {
                setContentTitle(applicationContext.getString(R.string.file_download_error))
                setContentText(errorDetails)
                setSmallIcon(R.mipmap.ic_launcher)
                setAutoCancel(true)

            }
        notificationManager.notify(666, errorDownload.build())
        EventBus.getDefault().post(FileDownloadEnded(fileId))
        return Result.failure()
    }

    private fun addFileIdToSet(id: Long) {
        if (downloadingFilesIdSet == null) downloadingFilesIdSet = hashSetOf()
        downloadingFilesIdSet?.add(id)
    }

    private fun removeFileIdFromSet(id: Long) {
        downloadingFilesIdSet?.run {
            remove(id)
            if (size == 0) {
                downloadingFilesIdSet = null
            }
        }
    }

    private fun getString(@StringRes res: Int): String {
        return applicationContext.getString(res)
    }

    companion object {
        var downloadingFilesIdSet: HashSet<Long>? = null
        const val UPLOAD_CHANNEL_ID = "UPLOAD_CHANNEL"
        const val FILE_TYPE_PARAM = "fileType"
        const val FILE_URL_PARAM = "fileUrl"
        const val FILE_ID_PARAM = "fileId"
        const val fileNamePrefix = "pixabayFile"

        const val Progress = "Progress"
        private const val delayDuration = 1L

    }

    enum class DownloadFileType {
        IMAGE,
        VIDEO
    }
}