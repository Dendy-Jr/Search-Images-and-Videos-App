package com.dendi.android.search_images_and_videos_app.data.image.cache

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Dao
interface ImageDao {

    @Query("SELECT * FROM ${ImageCache.TABLE_IMAGES} ORDER BY data_time DESC")
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