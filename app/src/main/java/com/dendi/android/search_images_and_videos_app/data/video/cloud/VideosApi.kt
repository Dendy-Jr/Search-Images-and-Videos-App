package com.dendi.android.search_images_and_videos_app.data.video.cloud

import retrofit2.http.GET
import retrofit2.http.Query

interface VideosApi {
//    .?safesearch=true
    @GET("videos/")
    suspend fun searchVideo(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): VideoResponse
}