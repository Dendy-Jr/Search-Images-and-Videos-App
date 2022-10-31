package ui.dendi.finder.favorites_data.images

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.core.core.models.Image
import java.util.Date
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesLocalDataSource @Inject constructor(
    private val dao: FavoritesImageDao
) {

    fun getFavoritesImage(): Flow<Result<List<Image>>> =
        dao.getFavoritesImage().map { images ->
            Result.success(images.map {
                it.toDomain()
            })
        }

    suspend fun insertImage(image: Image) =
        dao.insertImage(image.toFavorite())

    suspend fun deleteImage(image: Image) =
        dao.deleteImage(image.toFavorite())

    suspend fun deleteAllImage() = dao.clearAll()

    private fun Image.toFavorite() = FavoriteImage(
        id = id,
        collections = collections,
        comments = comments,
        downloads = downloads,
        imageHeight = imageHeight,
        imageSize = imageSize,
        imageWidth = imageWidth,
        largeImageURL = largeImageURL,
        likes = likes,
        pageURL = pageURL,
        previewHeight = previewHeight,
        previewURL = previewURL,
        previewWidth = previewWidth,
        tags = tags,
        type = type,
        user = user,
        userImageURL = userImageURL,
        views = views,
        webFormatHeight = webFormatHeight,
        webFormatURL = webFormatURL,
        webFormatWidth = webFormatWidth,
        date = Date.from(off?.toInstant()),
    )
}