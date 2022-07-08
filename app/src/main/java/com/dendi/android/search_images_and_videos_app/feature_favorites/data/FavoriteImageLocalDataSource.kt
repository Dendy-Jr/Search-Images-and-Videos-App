package com.dendi.android.search_images_and_videos_app.feature_favorites.data

import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
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
