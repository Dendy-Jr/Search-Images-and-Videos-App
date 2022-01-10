package com.dendi.android.search_images_and_videos_app.data.image.cloud


/**
 * @author Dendy-Jr on 26.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ImagesRemoteDataSource {

    suspend fun getImages(
        query: String,
        page: Int,
        perPage: Int
    ): List<ImageCloud>

    class ImagesRemoteDataSourceImpl(private val imagesApi: ImagesApi) : ImagesRemoteDataSource {
        override suspend fun getImages(
            query: String,
            page: Int,
            perPage: Int
        ): List<ImageCloud> {
            return imagesApi.searchImages(query, page, perPage).hits
        }
    }
}