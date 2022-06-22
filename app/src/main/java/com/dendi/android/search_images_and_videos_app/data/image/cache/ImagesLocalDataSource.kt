package com.dendi.android.search_images_and_videos_app.data.image.cache

import com.dendi.android.search_images_and_videos_app.domain.image.Image
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesLocalDataSource @Inject constructor(
    private val imageDao: ImageDao,
) {

    fun getImages(): Flow<List<Image>> =
        imageDao.getImages().map {
            it.map { it.toDomain() }
        }

    suspend fun insertAllImages(images: List<Image>) =
        imageDao.insertAll(images.map { it.toCache() })

    suspend fun insertImage(image: Image) =
        imageDao.insertImage(image.toCache())

    suspend fun deleteImage(image: Image) =
        imageDao.deleteImage(image.toCache())

    suspend fun deleteAllImages() =
        imageDao.clearAll()
}