package com.dendi.android.search_images_and_videos_app.data.image.cloud


/**
 * @author Dendy-Jr on 26.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ImagesRemoteDataSource {

    suspend fun getImages(): List<ImageDto>

    class ImagesRemoteDataSourceImpl(private val imagesApi: ImagesApi): ImagesRemoteDataSource {
        override suspend fun getImages(): List<ImageDto> {
           return imagesApi.searchImages().hits
        }
    }
}