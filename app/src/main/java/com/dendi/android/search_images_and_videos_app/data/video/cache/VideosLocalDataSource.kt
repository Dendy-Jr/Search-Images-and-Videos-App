package com.dendi.android.search_images_and_videos_app.data.video.cache

import com.dendi.android.search_images_and_videos_app.domain.video.Video
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosLocalDataSource @Inject constructor(
    private val videoDao: VideoDao,
) {
//    fun getPagingVideo(): PagingSource<Int, Video> {
//        return videoDao.getPagingVideo()
//    }

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