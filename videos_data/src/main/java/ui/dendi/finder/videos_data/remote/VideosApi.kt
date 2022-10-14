package ui.dendi.finder.videos_data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface VideosApi {

    @GET("videos/?safesearch=true")
    suspend fun searchVideo(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("video_type") type: String?,
        @Query("category") category: String?,
        @Query("order") order: String?,
    ): VideoResponse
}