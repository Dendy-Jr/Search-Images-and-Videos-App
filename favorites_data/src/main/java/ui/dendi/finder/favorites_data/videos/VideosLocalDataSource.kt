package ui.dendi.finder.favorites_data.videos

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ui.dendi.finder.core.core.models.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosLocalDataSource @Inject constructor(
    private val dao: FavoritesVideoDao,
) {

    fun getVideos(): Flow<List<Video>> =
        dao.getVideos().map {
            it.toDomain()
        }

    suspend fun insertImage(image: Video) =
        dao.insertImage(image.toCache())

    suspend fun deleteImage(image: Video) =
        dao.deleteVideo(image.toCache())

    suspend fun deleteAllVideos() =
        dao.deleteAllVideos()

    private fun Video.toCache() = FavoriteVideo(
        id = id,
        comments = comments,
        downloads = downloads,
        duration = duration,
        likes = likes,
        pageURL = pageURL,
        pictureId = pictureId,
        tags = tags,
        type = type,
        user = user,
        userId = userId,
        userImageURL = userImageURL,
        videos = videos.toDomain(),
        views = views,
    )

    private fun List<FavoriteVideo>.toDomain(): List<Video> =
        map { it.toDomain() }

    private fun VideosStreams.toDomain() = VideosStreamsCache(
        large = large.toCache(),
        medium = medium.toCache(),
        small = small.toCache(),
        tiny = tiny.toCache(),
    )

    private fun Large.toCache() = LargeCache(height = height, size = size, url = url, width = width)
    private fun Medium.toCache() =
        MediumCache(height = height, size = size, url = url, width = width)

    private fun Small.toCache() = SmallCache(height = height, size = size, url = url, width = width)
    private fun Tiny.toCache() = TinyCache(height = height, size = size, url = url, width = width)
}