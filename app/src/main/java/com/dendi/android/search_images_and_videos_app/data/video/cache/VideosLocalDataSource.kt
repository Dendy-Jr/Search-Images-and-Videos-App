package com.dendi.android.search_images_and_videos_app.data.video.cache

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
interface VideosLocalDataSource {

    fun getPagingVideo(): PagingSource<Int, VideoEntity>
    fun getVideos(): Flow<List<VideoEntity>>
    suspend fun insertAllVideos(images: List<VideoEntity>)
    suspend fun insertImage(image: VideoEntity)
    suspend fun deleteImage(image: VideoEntity)
    suspend fun deleteAllVideos()

    class VideosLocalDataSourceImpl(
        private val videoDao: VideoDao
    ) : VideosLocalDataSource {
        override fun getPagingVideo(): PagingSource<Int, VideoEntity> {
            return videoDao.getPagingVideo()
        }

        override fun getVideos(): Flow<List<VideoEntity>> =
            videoDao.getVideos()

        override suspend fun insertAllVideos(images: List<VideoEntity>) =
            videoDao.insertAll(images)

        override suspend fun insertImage(image: VideoEntity) =
            videoDao.insertImage(image)

        override suspend fun deleteImage(image: VideoEntity) =
            videoDao.deleteVideo(image)

        override suspend fun deleteAllVideos() =
            videoDao.deleteAllVideos()
    }
}