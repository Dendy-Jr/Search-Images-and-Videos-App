package ui.dendi.finder.favorites_data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.app.feature_favorites.data.FavoriteImageDao
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.images_data.local.ImageEntity
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageLocalDataSource @Inject constructor(
    private val dao: FavoriteImageDao,
) {
    fun getFavoritesImage(): Flow<Result<List<Image>>> =
        dao.getFavoritesImage().map {
            Result.success(it.map { it.image.toDomain() })
        }

    suspend fun insertImage(image: Image) =
        dao.insertImage(image.toFavorite())

    suspend fun deleteImage(image: Image) =
        dao.deleteImage(image.toFavorite())

    suspend fun deleteAllImages() = dao.clearAll()

    private fun Image.toFavorite() = FavoriteImage(
        id = id,
        image = ImageEntity(
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
    )
}
