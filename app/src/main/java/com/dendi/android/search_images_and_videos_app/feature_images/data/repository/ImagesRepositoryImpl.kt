package com.dendi.android.search_images_and_videos_app.feature_images.data.repository

import androidx.paging.*
import com.dendi.android.search_images_and_videos_app.core.db.PixabayDb
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImageDao
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImagesLocalDataSource
import com.dendi.android.search_images_and_videos_app.feature_images.data.local.ImagesRemoteMediator
import com.dendi.android.search_images_and_videos_app.feature_images.data.remote.ImagesRemoteDataSource
import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import com.dendi.android.search_images_and_videos_app.feature_images.domain.repository.ImagesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
class ImagesRepositoryImpl @Inject constructor(
    private val localDataSource: ImagesLocalDataSource,
    private val remoteDataSource: ImagesRemoteDataSource,
    private val imageDao: ImageDao,
    private val database: PixabayDb
) : ImagesRepository {

    override fun getPagedItems(query: String): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            remoteMediator = ImagesRemoteMediator(remoteDataSource, query, database),
            pagingSourceFactory = {
                imageDao.getImagesPagingSource()
            },
        ).flow
            .map { pagingData ->
                pagingData.map {
                    it.toDomain()
                }
            }
    }


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
        const val PAGE_SIZE = 30
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