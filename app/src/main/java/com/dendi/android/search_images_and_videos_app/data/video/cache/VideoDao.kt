package com.dendi.android.search_images_and_videos_app.data.video.cache

import androidx.paging.PagingSource
import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Dao
interface VideoDao {

    @Query("SELECT * FROM ${VideoCache.TABLE_VIDEOS}")
    fun getPagingVideo(): PagingSource<Int, VideoCache>

    @Query("SELECT * FROM ${VideoCache.TABLE_VIDEOS}")
    fun getVideos(): Flow<List<VideoCache>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(images: List<VideoCache>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: VideoCache)

    @Delete
    suspend fun deleteVideo(image: VideoCache)

    @Query("DELETE FROM ${VideoCache.TABLE_VIDEOS}")
    suspend fun deleteAllVideos()
}