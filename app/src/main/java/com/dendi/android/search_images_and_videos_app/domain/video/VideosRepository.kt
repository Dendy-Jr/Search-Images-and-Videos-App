package com.dendi.android.search_images_and_videos_app.domain.video

import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoEntity
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 28.12.2021
 * olehvynnytskyi@gmail.com
 */
interface VideosRepository {

    fun getSearchResult(query: String): Flow<PagingData<VideoEntity>>
    fun getPagingVideo(): PagingSource<Int, VideoEntity>
    fun getVideos(): Flow<List<VideoEntity>>
    suspend fun insertAllVideos(images: List<VideoEntity>)
    suspend fun insertVideo(image: VideoEntity)
    suspend fun deleteVideo(image: VideoEntity)
    suspend fun deleteAllVideos()
}