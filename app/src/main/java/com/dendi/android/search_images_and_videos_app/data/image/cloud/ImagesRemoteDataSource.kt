package com.dendi.android.search_images_and_videos_app.data.image.cloud

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRemoteDataSource @Inject constructor(
    private val imagesApi: ImagesApi,
) {
    suspend fun getImages(query: String, page: Int, perPage: Int): List<ImageResponseItem> =
        imagesApi.searchImages(query, page, perPage).hits
}