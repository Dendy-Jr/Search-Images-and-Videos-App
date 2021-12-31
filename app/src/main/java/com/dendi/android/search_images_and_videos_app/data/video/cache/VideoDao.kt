package com.dendi.android.search_images_and_videos_app.data.video.cache

import androidx.paging.PagingSource
import androidx.room.*
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Dao
interface VideoDao {

    @Query("SELECT * FROM ${VideoEntity.TABLE_VIDEOS}")
    fun getPagingVideo(): PagingSource<Int, VideoEntity>

    @Query("SELECT * FROM ${VideoEntity.TABLE_VIDEOS}")
    fun getVideos(): Flow<List<VideoEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<VideoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: VideoEntity)

    @Delete
    suspend fun deleteVideo(image: VideoEntity)

    @Query("DELETE FROM ${VideoEntity.TABLE_VIDEOS}")
    suspend fun deleteAllVideos()
}