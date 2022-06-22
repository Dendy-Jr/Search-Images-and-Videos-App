package com.dendi.android.search_images_and_videos_app.data.image.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM ${ImageCache.TABLE_IMAGES} ORDER BY date DESC")
    fun getImages(): Flow<List<ImageCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageCache)

    @Delete
    suspend fun deleteImage(image: ImageCache)

    @Query("DELETE FROM ${ImageCache.TABLE_IMAGES}")
    suspend fun clearAll()
}