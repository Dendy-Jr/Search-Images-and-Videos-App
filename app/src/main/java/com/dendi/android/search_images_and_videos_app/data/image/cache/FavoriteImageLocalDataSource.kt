package com.dendi.android.search_images_and_videos_app.data.image.cache

import com.dendi.android.search_images_and_videos_app.domain.image.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageLocalDataSource @Inject constructor(
    private val dao: FavoriteImageDao,
) {
    fun getFavoritesImage(): Flow<List<Image>> =
        dao.getFavoritesImage().map {
            it.map { it.image.toDomain() }
        }

    suspend fun insertImage(image: Image) =
        dao.insertImage(image.toFavorite())

    suspend fun deleteImage(image: Image) =
        dao.deleteImage(image.toFavorite())

    suspend fun deleteAllImages() = dao.clearAll()
}
