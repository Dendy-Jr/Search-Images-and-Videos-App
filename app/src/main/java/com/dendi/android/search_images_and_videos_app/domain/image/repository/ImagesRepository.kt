package com.dendi.android.search_images_and_videos_app.domain.image.repository

import androidx.paging.PagingData
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 25.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ImagesRepository {

    fun searchImage(query: String): Flow<PagingData<Image>>
    fun getImages(): Flow<List<Image>>
    suspend fun insertAllImages(images: List<Image>)
    suspend fun insertImage(image: Image)
    suspend fun deleteImage(image: Image)
    suspend fun deleteAllImages()
}