package ui.dendi.finder.images_data.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.core.core.models.Image
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesLocalDataSource @Inject constructor(
    private val imageDao: ImageDao,
) {

    fun getImages(): Flow<List<Image>> =
        imageDao.getImages().map { list ->
            list.map { it.toDomain() }
        }

    suspend fun insertAllImages(images: List<Image>) =
        imageDao.insertAll(images.map { it.toCache() })

    suspend fun insertImage(image: Image) =
        imageDao.insertImage(image.toCache())

    suspend fun deleteImage(image: Image) =
        imageDao.deleteImage(image.toCache())

    suspend fun deleteAllImages() =
        imageDao.clearAll()

    private fun Image.toCache() = ImageEntity(
        collections = collections,
        comments = comments,
        downloads = downloads,
        id = id,
        localId = id,
        imageHeight = imageHeight,
        imageSize = imageSize,
        imageWidth = imageWidth,
        largeImageURL = largeImageURL,
        likes = likes,
        pageURL = pageURL,
        previewWidth = previewWidth,
        previewHeight = previewHeight,
        previewURL = previewURL,
        tags = tags,
        type = type,
        user = user,
        userImageURL = userImageURL,
        views = views,
        webFormatHeight = webFormatHeight,
        webFormatURL = webFormatURL,
        webFormatWidth = webFormatWidth,
        date = Calendar.getInstance().time,
    )
}