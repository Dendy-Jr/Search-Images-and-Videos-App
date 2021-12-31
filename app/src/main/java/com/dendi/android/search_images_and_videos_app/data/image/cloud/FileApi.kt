package com.dendi.android.search_images_and_videos_app.data.image.cloud

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * @author Dendy-Jr on 30.12.2021
 * olehvynnytskyi@gmail.com
 */
interface FileApi {
    @Streaming
    @GET
    suspend fun downloadFile(@Url url: String): ResponseBody
}