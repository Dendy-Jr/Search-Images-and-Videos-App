package com.dendi.android.search_images_and_videos_app.data.image.cloud

import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET(".?safesearch=true")
    suspend fun searchImages(
        @Query("q") query: String = "cat",
        @Query("page") page: Int = 1,
        @Query("per_page") perPage: Int = 200,
    ): ImagesResponse
}