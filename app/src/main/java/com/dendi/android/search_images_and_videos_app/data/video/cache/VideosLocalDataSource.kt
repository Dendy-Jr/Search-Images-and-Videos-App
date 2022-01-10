package com.dendi.android.search_images_and_videos_app.data.video.cache

import androidx.paging.PagingSource
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
interface VideosLocalDataSource {

    fun getPagingVideo(): PagingSource<Int, VideoCache>
    fun getVideos(): Flow<List<VideoCache>>
    suspend fun insertAllVideos(images: List<VideoCache>)
    suspend fun insertImage(image: VideoCache)
    suspend fun deleteImage(image: VideoCache)
    suspend fun deleteAllVideos()

    class VideosLocalDataSourceImpl(
        private val videoDao: VideoDao
    ) : VideosLocalDataSource {
        override fun getPagingVideo(): PagingSource<Int, VideoCache> {
            return videoDao.getPagingVideo()
        }

        override fun getVideos(): Flow<List<VideoCache>> =
            videoDao.getVideos()

        override suspend fun insertAllVideos(images: List<VideoCache>) =
            videoDao.insertAll(images)

        override suspend fun insertImage(image: VideoCache) =
            videoDao.insertImage(image)

        override suspend fun deleteImage(image: VideoCache) =
            videoDao.deleteVideo(image)

        override suspend fun deleteAllVideos() =
            videoDao.deleteAllVideos()
    }
}