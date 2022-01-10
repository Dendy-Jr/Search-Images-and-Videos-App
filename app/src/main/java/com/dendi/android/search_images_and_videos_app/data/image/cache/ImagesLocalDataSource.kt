package com.dendi.android.search_images_and_videos_app.data.image.cache

import kotlinx.coroutines.flow.Flow


/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ImagesLocalDataSource {

    fun getImages(): Flow<List<ImageCache>>
    suspend fun insertAllImages(images: List<ImageCache>)
    suspend fun insertImage(image: ImageCache)
    suspend fun deleteImage(image: ImageCache)
    suspend fun deleteAllImages()

    class ImagesLocalDataSourceImpl(
        private val imageDao: ImageDao
    ) : ImagesLocalDataSource {

        override fun getImages(): Flow<List<ImageCache>> =
            imageDao.getImages()

        override suspend fun insertAllImages(images: List<ImageCache>) =
            imageDao.insertAll(images)

        override suspend fun insertImage(image: ImageCache) =
            imageDao.insertImage(image)

        override suspend fun deleteImage(image: ImageCache) =
            imageDao.deleteImage(image)

        override suspend fun deleteAllImages() =
            imageDao.clearAll()
    }
}