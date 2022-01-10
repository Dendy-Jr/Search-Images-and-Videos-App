package com.dendi.android.search_images_and_videos_app.domain.video.repository

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 28.12.2021
 * olehvynnytskyi@gmail.com
 */
interface VideosRepository {

    fun searchVideo(query: String): Flow<PagingData<VideoCache>>
    fun getPagingVideo(): PagingSource<Int, VideoCache>
    fun getVideos(): Flow<List<VideoCache>>
    suspend fun insertAllVideos(videos: List<VideoCache>)
    suspend fun insertVideo(video: VideoCache)
    suspend fun deleteVideo(video: VideoCache)
    suspend fun deleteAllVideos()
}