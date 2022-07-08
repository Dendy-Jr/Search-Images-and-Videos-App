package com.dendi.android.search_images_and_videos_app.feature_videos.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface VideosApi {

    @GET("videos/?safesearch=true&editors_choice=true")
    suspend fun searchVideo(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
    ): VideoResponse
}