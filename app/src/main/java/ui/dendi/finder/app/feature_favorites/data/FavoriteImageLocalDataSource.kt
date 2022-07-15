package ui.dendi.finder.app.feature_favorites.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.app.feature_images.domain.Image
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
}
