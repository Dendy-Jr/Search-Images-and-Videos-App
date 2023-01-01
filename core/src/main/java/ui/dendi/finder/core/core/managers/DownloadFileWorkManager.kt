package ui.dendi.finder.core.core.managers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import ui.dendi.finder.core.R
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@HiltWorker
class DownloadFileWorkManager @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted private val workerParameters: WorkerParameters,
) : CoroutineWorker(context, workerParameters) {

    private val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override suspend fun doWork(): Result {
        displayNotification()

        val mimeType = when (workerParameters.inputData.getString(KEY_FILE_TYPE)) {
            JPG -> "image/jpg"
            MP4 -> "video/mp4"
            else -> ""
        }
        val filename = workerParameters.inputData.getString(KEY_FILE_NAME)
        val url = workerParameters.inputData.getString(KEY_FILE_URL)
        url?.let {
            return try {
                val uri = downloadFileFromUri(url, mimeType, filename)
                notificationManager.cancel(NOTIFICATION_ID)

                Result.success(workDataOf(KEY_FILE_URI to uri.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure()
            }
        }
        return Result.failure()
    }

    private fun displayNotification() {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT,
        ).apply {
            enableVibration(true)
            enableLights(true)
        }
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(CHANNEL_DESC)
            .setOngoing(true)
            .setProgress(0, 0, true)

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun downloadFileFromUri(url: String, mimeType: String, filename: String?): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            return if (uri != null) {
                URL(url).openStream().use { input ->
                    resolver.openOutputStream(uri).use { output ->
                        input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                    }
                }
                uri
            } else {
                null
            }
        } else {
            val target = File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                filename!!
            )
            URL(url).openStream().use { input ->
                FileOutputStream(target).use { output ->
                    input.copyTo(output)
                }
            }
            return target.toUri()
        }
    }

    companion object {
        const val CHANNEL_DESC = "Downloading"
        const val CHANNEL_ID = "channel_100"
        const val CHANNEL_NAME = "Download File"
        const val JPG = "JPG"
        const val KEY_FILE_NAME = "file_name"
        const val KEY_FILE_TYPE = "file_type"
        const val KEY_FILE_URI = "file_uri"
        const val KEY_FILE_URL = "file_url"
        const val MP4 = "MP4"
        const val NOTIFICATION_ID = 100
    }
}