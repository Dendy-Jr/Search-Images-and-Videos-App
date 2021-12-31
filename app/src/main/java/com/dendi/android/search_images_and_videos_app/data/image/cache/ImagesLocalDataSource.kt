package com.dendi.android.search_images_and_videos_app.data.image.cache

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow


/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ImagesLocalDataSource {

    fun getPagingImage(): PagingSource<Int, ImageEntity>
    fun getImages(): Flow<List<ImageEntity>>
    suspend fun insertAllImages(images: List<ImageEntity>)
    suspend fun insertImage(image: ImageEntity)
    suspend fun deleteImage(image: ImageEntity)
    suspend fun deleteAllImages()

    class ImagesLocalDataSourceImpl(
        private val imageDao: ImageDao
    ) : ImagesLocalDataSource {
        override fun getPagingImage(): PagingSource<Int, ImageEntity> =
            imageDao.getPagingImage()

        override fun getImages(): Flow<List<ImageEntity>> =
            imageDao.getImages()

        override suspend fun insertAllImages(images: List<ImageEntity>) =
            imageDao.insertAll(images)

        override suspend fun insertImage(image: ImageEntity) =
            imageDao.insertImage(image)

        override suspend fun deleteImage(image: ImageEntity) =
            imageDao.deleteImage(image)

        override suspend fun deleteAllImages() =
            imageDao.clearAll()
    }
}