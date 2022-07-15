package ui.dendi.finder.app.core

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.yield
import timber.log.Timber
import ui.dendi.finder.app.core.network.DownloadFileApi
import java.io.File
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton

private const val DIR_NAME = "downloads"

@Singleton
class DownloadManager @Inject constructor(
    private val api: DownloadFileApi,
    @ApplicationContext private val context: Context,
) {

    @Suppress("BlockingMethodInNonBlockingContext")
    suspend fun download(url: String, fileName: String): Result<Unit> {
        val file = File(getDir(), fileName)
        Timber.d(file.path)
        return Result.success(api.downloadFile(url)
            .byteStream().use { inputStream ->
                inputStream.copyToFile(
                    file = file,
                )
            })
    }

    @Suppress("BlockingMethodInNonBlockingContext")
    private suspend fun InputStream.copyToFile(
        file: File,
    ) = withContext(Dispatchers.IO) {
        use {
            file.outputStream().use { output ->
                var bytesCopied: Long = 0
                val buffer = ByteArray(8 * 1024)
                var bytes = read(buffer)
                while (bytes >= 0) {
                    output.write(buffer, 0, bytes)
                    bytesCopied += bytes
                    bytes = read(buffer)
                    yield()
                }
            }
        }
    }

    private fun getDir(): File = File(context.filesDir, DIR_NAME).also {
        it.mkdir()
    }
}