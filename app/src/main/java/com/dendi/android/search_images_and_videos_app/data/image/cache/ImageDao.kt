package com.dendi.android.search_images_and_videos_app.data.image.cache

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Dao
interface ImageDao {

    @Query("SELECT * FROM ${ImageEntity.TABLE_IMAGES}")
    fun getPagingImage(): PagingSource<Int, ImageEntity>

    @Query("SELECT * FROM ${ImageEntity.TABLE_IMAGES} ORDER BY data_time DESC")
    fun getImages(): Flow<List<ImageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<ImageEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: ImageEntity)

    @Delete
    suspend fun deleteImage(image: ImageEntity)

    @Query("DELETE FROM ${ImageEntity.TABLE_IMAGES}")
    suspend fun clearAll()
}