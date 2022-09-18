package ui.dendi.finder.images_data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ui.dendi.finder.images_data.remote.ImagesResponse

interface ImagesApi {

    @GET("?safesearch=true")
    suspend fun searchImages(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("image_type") type: String,
        @Query("category") category: String,
        @Query("orientation") orientation: String,
        @Query("colors") colors: String,
    ): ImagesResponse
}