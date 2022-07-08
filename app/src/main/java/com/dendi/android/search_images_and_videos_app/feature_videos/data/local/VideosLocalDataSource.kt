package com.dendi.android.search_images_and_videos_app.feature_videos.data.local

import com.dendi.android.search_images_and_videos_app.feature_videos.domain.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosLocalDataSource @Inject constructor(
    private val videoDao: VideoDao,
) {

    fun getVideos(): Flow<List<VideoCache>> =
        videoDao.getVideos()

    suspend fun insertAllVideos(images: List<Video>) =
        videoDao.insertAll(images.map { it.toCache() })

    suspend fun insertImage(image: Video) =
        videoDao.insertImage(image.toCache())

    suspend fun deleteImage(image: Video) =
        videoDao.deleteVideo(image.toCache())

    suspend fun deleteAllVideos() =
        videoDao.deleteAllVideos()
}