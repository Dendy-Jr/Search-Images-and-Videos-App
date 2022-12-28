package ui.dendi.finder.videos_data.remote

import ui.dendi.finder.core.core.models.Video
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRemoteDataSource @Inject constructor(
    private val api: VideosApi,
) {
    suspend fun getVideos(
        category: String?,
        order: String?,
        page: Int,
        perPage: Int,
        query: String,
        type: String?,
    ): List<Video> =
        api.searchVideo(query, page, perPage, type, category, order).hits.toDomain()

    private fun VideoCloud.toDomain() = Video(
        comments = comments,
        date = "",
        downloads = downloads,
        duration = duration,
        id = id,
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