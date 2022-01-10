package com.dendi.android.search_images_and_videos_app.data.video.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideosLocalDataSource
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideosApi
import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideoPagingSource
import com.dendi.android.search_images_and_videos_app.data.video.mapper.VideoCloudToCacheMapper
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 12.12.2021
 * olehvynnytskyi@gmail.com
 */
class VideosRepositoryImpl(
    private val service: VideosApi,
    private val localDataSource: VideosLocalDataSource,
    private val mapper: VideoCloudToCacheMapper
) : VideosRepository {

    override fun searchVideo(query: String): Flow<PagingData<VideoCache>> =
        Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = { VideoPagingSource(service, query, mapper) }
        ).flow

    override fun getPagingVideo(): PagingSource<Int, VideoCache> {
        return localDataSource.getPagingVideo()
    }

    override fun getVideos(): Flow<List<VideoCache>> {
        return localDataSource.getVideos()
    }

    override suspend fun insertAllVideos(videos: List<VideoCache>) {
        return localDataSource.insertAllVideos(videos)
    }

    override suspend fun insertVideo(video: VideoCache) {
        return localDataSource.insertImage(video)
    }

    override suspend fun deleteVideo(video: VideoCache) {
        return localDataSource.deleteImage(video)
    }

    override suspend fun deleteAllVideos() {
        return localDataSource.deleteAllVideos()
    }
}