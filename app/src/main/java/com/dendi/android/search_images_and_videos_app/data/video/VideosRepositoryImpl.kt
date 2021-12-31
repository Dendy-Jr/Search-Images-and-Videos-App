package com.dendi.android.search_images_and_videos_app.data.video

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoEntity
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideosLocalDataSource
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideoDto
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideosApi
import com.dendi.android.search_images_and_videos_app.domain.video.VideosRepository
import com.dendi.android.search_images_and_videos_app.presentation.video.VideoPagingSource
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 12.12.2021
 * olehvynnytskyi@gmail.com
 */
class VideosRepositoryImpl(
    private val service: VideosApi,
    private val localDataSource: VideosLocalDataSource
) : VideosRepository {

    override fun getSearchResult(query: String): Flow<PagingData<VideoEntity>> =
        Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = { VideoPagingSource(service, query) }
        ).flow

    override fun getPagingVideo(): PagingSource<Int, VideoEntity> {
       return localDataSource.getPagingVideo()
    }

    override fun getVideos(): Flow<List<VideoEntity>> {
        return localDataSource.getVideos()
    }

    override suspend fun insertAllVideos(images: List<VideoEntity>) {
        return localDataSource.insertAllVideos(images)
    }

    override suspend fun insertVideo(image: VideoEntity) {
        return localDataSource.insertImage(image)
    }

    override suspend fun deleteVideo(image: VideoEntity) {
        return localDataSource.deleteImage(image)
    }

    override suspend fun deleteAllVideos() {
        return localDataSource.deleteAllVideos()
    }
}