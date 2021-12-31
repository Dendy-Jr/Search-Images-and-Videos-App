package com.dendi.android.search_images_and_videos_app.domain.image

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 25.12.2021
 * olehvynnytskyi@gmail.com
 */
interface ImagesRepository {

    fun getSearchResult(query: String): Flow<PagingData<ImageEntity>>
    fun getPagingImage(): PagingSource<Int, ImageEntity>
    fun getImages(): Flow<List<ImageEntity>>
    suspend fun insertAllImages(images: List<ImageEntity>)
    suspend fun insertImage(image: ImageEntity)
    suspend fun deleteImage(image: ImageEntity)
    suspend fun deleteAllImages()
}