package ui.dendi.finder.app.feature_videos.data.remote

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideosRemoteDataSource @Inject constructor(
    private val api: VideosApi,
) {
    suspend fun getVideos(query: String, page: Int, perPage: Int): List<VideoCloud> =
        api.searchVideo(query, page, perPage).hits
}