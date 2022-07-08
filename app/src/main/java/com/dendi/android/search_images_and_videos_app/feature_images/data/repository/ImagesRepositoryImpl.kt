package com.dendi.android.search_images_and_videos_app.feature_images.data.repository

import androidx.paging.*
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImagesLocalDataSource
import com.dendi.android.search_images_and_videos_app.feature_images.data.remote.ImagesRemoteDataSource
import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import com.dendi.android.search_images_and_videos_app.feature_images.domain.repository.ImagesRepository
import com.dendi.android.search_images_and_videos_app.feature_images.data.remote.ImagesPagingSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
class ImagesRepositoryImpl @Inject constructor(
    private val localDataSource: ImagesLocalDataSource,
    private val remoteDataSource: ImagesRemoteDataSource,
) : ImagesRepository {

    override fun getPagedItems(query: String): Flow<PagingData<Image>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                initialLoadSize = PAGE_SIZE,
                prefetchDistance = PAGE_SIZE / 2,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagesPagingSource(
                    remoteDataSource = remoteDataSource,
                    localDataSource = localDataSource,
                    query = query,
                )
            },
        ).flow

    override fun getItems(): Flow<List<Image>> =
        localDataSource.getImages()

    override suspend fun insertAllItems(items: List<Image>) {
        localDataSource.insertAllImages(items)
    }

    override suspend fun insertItem(item: Image) {
        localDataSource.insertImage(item)
    }

    override suspend fun deleteItem(item: Image) {
        localDataSource.insertImage(item)
    }

    override suspend fun deleteAllItems() = localDataSource.deleteAllImages()

    private companion object {
        const val PAGE_SIZE = 40
    }
}

@ExperimentalPagingApi
@Module
@InstallIn(SingletonComponent::class)
interface ImagesRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: ImagesRepositoryImpl): ImagesRepository
}