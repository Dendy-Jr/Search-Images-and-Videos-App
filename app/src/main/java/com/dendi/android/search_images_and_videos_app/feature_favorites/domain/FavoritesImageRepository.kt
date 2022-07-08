package com.dendi.android.search_images_and_videos_app.feature_favorites.domain

import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import kotlinx.coroutines.flow.Flow

interface FavoritesImageRepository {

    fun getFavoritesImage(): Flow<Result<List<Image>>>
    suspend fun insertImage(image: Image)
    suspend fun deleteImage(image: Image)
    suspend fun deleteAllImage()
}