package com.dendi.android.search_images_and_videos_app.data.image

import androidx.paging.*
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImagesLocalDataSource
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesApi
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesRemoteDataSource
import com.dendi.android.search_images_and_videos_app.domain.image.ImagesRepository
import com.dendi.android.search_images_and_videos_app.presentation.image.ImagePagingSource
import kotlinx.coroutines.flow.Flow

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
@ExperimentalPagingApi
class ImagesRepositoryImpl(
    private val service: ImagesApi,
    private val localDataSource: ImagesLocalDataSource,
    private val remoteDataSource: ImagesRemoteDataSource
) : ImagesRepository {

    override fun getSearchResult(query: String): Flow<PagingData<ImageEntity>> =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
//            remoteMediator = ImageRemoteMediator(
//                service,
//                query,
//                database
//            ),
//            pagingSourceFactory = { localDataSource.getPagingImage() }
            pagingSourceFactory = { ImagePagingSource(service, query) }
        ).flow

    override fun getPagingImage(): PagingSource<Int, ImageEntity> =
        localDataSource.getPagingImage()

    override fun getImages(): Flow<List<ImageEntity>> =
        localDataSource.getImages()

    override suspend fun insertAllImages(images: List<ImageEntity>) =
        localDataSource.insertAllImages(images)

    override suspend fun insertImage(image: ImageEntity) {
        localDataSource.insertImage(image)
    }

    override suspend fun deleteImage(image: ImageEntity) =
        localDataSource.deleteImage(image)

    override suspend fun deleteAllImages() = localDataSource.deleteAllImages()
}