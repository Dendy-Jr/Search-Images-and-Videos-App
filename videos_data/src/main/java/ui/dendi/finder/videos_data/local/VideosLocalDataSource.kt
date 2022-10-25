package ui.dendi.finder.videos_data.local

import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.core.core.models.*
import javax.inject.Inject
import javax.inject.Singleton

//TODO Delete it

//@Singleton
//class VideosLocalDataSource @Inject constructor(
//    private val videoDao: VideoDao,
//) {
//
//    fun getVideos(): Flow<List<VideoCache>> =
//        videoDao.getVideos()
//
//    suspend fun insertImage(image: Video) =
//        videoDao.insertImage(image.toCache())
//
//    suspend fun deleteImage(image: Video) =
//        videoDao.deleteVideo(image.toCache())
//
//    suspend fun deleteAllVideos() =
//        videoDao.deleteAllVideos()
//
//    private fun Video.toCache() = VideoCache(
//        id = id,
//        comments = comments,
//        downloads = downloads,
//        duration = duration,
//        likes = likes,
//        pageURL = pageURL,
//        pictureId = pictureId,
//        tags = tags,
//        type = type,
//        user = user,
//        userId = userId,
//        userImageURL = userImageURL,
//        videos = videos.toDomain(),
//        views = views,
//    )
//
//    private fun VideosStreams.toDomain() = VideosStreamsCache(
//        large = large.toCache(),
//        medium = medium.toCache(),
//        small = small.toCache(),
//        tiny = tiny.toCache(),
//    )
//
//    private fun Large.toCache() = LargeCache(height = height, size = size, url = url, width = width)
//    private fun Medium.toCache() = MediumCache(height = height, size = size, url = url, width = width)
//    private fun Small.toCache() = SmallCache(height = height, size = size, url = url, width = width)
//    private fun Tiny.toCache() = TinyCache(height = height, size = size, url = url, width = width)
//}