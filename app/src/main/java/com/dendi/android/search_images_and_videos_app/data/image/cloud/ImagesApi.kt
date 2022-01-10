package com.dendi.android.search_images_and_videos_app.data.image.cloud

import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ImagesApi {

    @GET(".?safesearch=true")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ImagesResponse
}