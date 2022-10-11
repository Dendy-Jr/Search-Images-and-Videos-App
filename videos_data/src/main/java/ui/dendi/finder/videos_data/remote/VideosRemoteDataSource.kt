package ui.dendi.finder.videos_data.remote

import ui.dendi.finder.core.core.models.Video
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRemoteDataSource @Inject constructor(
    private val api: VideosApi,
) {
    suspend fun getVideos(query: String = "cat", page: Int = 1, perPage: Int = 20): List<Video> =
        api.searchVideo(query, page, perPage).hits.toDomain()

    private fun VideoCloud.toDomain() = Video(
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

    private fun List<VideoCloud>.toDomain() = map { it.toDomain() }
}