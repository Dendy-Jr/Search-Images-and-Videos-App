package com.dendi.android.search_images_and_videos_app.data.image.repository

import androidx.paging.*
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImagesLocalDataSource
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesRemoteDataSource
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagePagingSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

class ImagesRepositoryImpl @Inject constructor(
    private val localDataSource: ImagesLocalDataSource,
    private val remoteDataSource: ImagesRemoteDataSource,
) : ImagesRepository {

    override fun search(query: String): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ImagePagingSource(remoteDataSource, query)
            }
        ).flow
    }

    override fun getItems(): Flow<List<Image>> = localDataSource.getImages()

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
}

@Module
@InstallIn(SingletonComponent::class)
interface ImagesRepositoryModule {

    @Binds
    @Singleton
    fun bind(impl: ImagesRepositoryImpl): ImagesRepository
}