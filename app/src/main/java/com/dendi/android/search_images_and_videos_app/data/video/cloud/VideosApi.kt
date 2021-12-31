package com.dendi.android.search_images_and_videos_app.data.video.cloud

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Dendy-Jr on 11.12.2021
 * olehvynnytskyi@gmail.com
 */
interface VideosApi {
//    .?safesearch=true
    @GET("videos/")
    suspend fun searchVideo(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): VideoResponse
}