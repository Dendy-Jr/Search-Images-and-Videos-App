package com.dendi.android.search_images_and_videos_app.data.video.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideosLocalDataSource
import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideoPagingSource
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideosRemoteDataSource
import com.dendi.android.search_images_and_videos_app.domain.video.Video
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

class VideosRepositoryImpl @Inject constructor(
    private val remoteDataSource: VideosRemoteDataSource,
    private val localDataSource: VideosLocalDataSource,
) : VideosRepository {

    override fun search(query: String): Flow<PagingData<Video>> =
        Pager(config = PagingConfig(
            pageSize = 20,
            enablePlaceholders = false
        ),
            pagingSourceFactory = {
                VideoPagingSource(remoteDataSource, query)
            }
        ).flow

    override fun getItems(): Flow<List<Video>> {
        return localDataSource.getVideos().map {
            it.toDomain()
        }
    }

    override suspend fun insertAllItems(items: List<Video>) {
        localDataSource.insertAllVideos(items)
    }

    override suspend fun insertItem(item: Video) {
        localDataSource.insertImage(item)
    }

    override suspend fun deleteItem(item: Video) {
        localDataSource.deleteImage(item)
    }

    override suspend fun deleteAllItems() {
        localDataSource.deleteAllVideos()
    }

    private fun List<VideoCache>.toDomain(): List<Video> =
        map { it.toDomain() }

    @JvmName("toDomainVideo")
    private fun List<Video>.toDomain(): List<VideoCache> =
        map { it.toCache() }
}

@Module
@InstallIn(SingletonComponent::class)
interface VideosRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: VideosRepositoryImpl): VideosRepository
}