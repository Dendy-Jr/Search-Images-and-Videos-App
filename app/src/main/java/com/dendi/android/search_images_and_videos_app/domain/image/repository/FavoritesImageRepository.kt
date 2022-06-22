package com.dendi.android.search_images_and_videos_app.domain.image.repository

import com.dendi.android.search_images_and_videos_app.domain.image.Image
import kotlinx.coroutines.flow.Flow

interface FavoritesImageRepository {

    fun getFavoritesImage(): Flow<List<Image>>
    suspend fun insertImage(image: Image)
    suspend fun deleteImage(image: Image)
    suspend fun deleteAllImage()
}