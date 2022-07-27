package ui.dendi.finder.app.feature_images.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesApi {

    @GET("?safesearch=true")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("image_type") type: String,
        @Query("category") category: String,
    ): ImagesResponse
}