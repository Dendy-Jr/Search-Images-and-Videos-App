package ui.dendi.finder.images_data.repository

import androidx.paging.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow
import ui.dendi.finder.images_data.local.ImagesLocalDataSource
import ui.dendi.finder.images_data.remote.ImagesPagingSource
import ui.dendi.finder.images_data.remote.ImagesRemoteDataSource
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.images_domain.repository.ImagesRepository
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalPagingApi
class ImagesRepositoryImpl @Inject constructor(
    private val localDataSource: ImagesLocalDataSource,
    private val remoteDataSource: ImagesRemoteDataSource,
//    private val imageDao: ImageDao,
//    private val database: PixabayDb,
) : ImagesRepository {

    override fun getPagedItems(
        query: String,
        type: String?,
        category: String?,
        orientation: String?,
        colors: String?,
    ): Flow<PagingData<Image>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
//            remoteMediator = ImagesRemoteMediator(remoteDataSource, query, imageDao),
            pagingSourceFactory = {
//                imageDao.getImagesPagingSource()
                ImagesPagingSource(
                    remoteDataSource,
                    localDataSource,
                    query,
                    type ?: "",
                    category ?: "",
                    orientation ?: "",
                    colors ?: ""
                )
            },
        ).flow
//            .map { pagingData ->
//                pagingData.map {
//                    it.toDomain()
//                }
//            }
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