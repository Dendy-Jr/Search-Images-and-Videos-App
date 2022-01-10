package com.dendi.android.search_images_and_videos_app.data.image.repository

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.*
import com.dendi.android.search_images_and_videos_app.core.forList
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImagesLocalDataSource
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesRemoteDataSource
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.mapper.ImageToImageCacheMapper
import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagePagingSource
import com.dendi.android.search_images_and_videos_app.data.image.mapper.ImageCacheToImageMapper
import com.dendi.android.search_images_and_videos_app.data.image.mapper.ImageCloudToImageMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
@ExperimentalPagingApi
class ImagesRepositoryImpl(
    private val localDataSource: ImagesLocalDataSource,
    private val remoteDataSource: ImagesRemoteDataSource,
    private val mapperImage: ImageCacheToImageMapper,
    private val mapperEntity: ImageToImageCacheMapper,
    private val mapperDtoToImage: ImageCloudToImageMapper
) : ImagesRepository {

    override fun searchImage(query: String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { ImagePagingSource(remoteDataSource, query, mapperDtoToImage) }
        ).flow

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getImages(): Flow<List<Image>> {
        return localDataSource.getImages().map { listImages ->
            mapperImage.forList().invoke(listImages)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertAllImages(images: List<Image>) {
        localDataSource.insertAllImages(images.map { image -> mapperEntity.map(image) })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun insertImage(image: Image) {
        localDataSource.insertImage(mapperEntity.map(image))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun deleteImage(image: Image) =
        localDataSource.deleteImage(mapperEntity.map(image))

    override suspend fun deleteAllImages() = localDataSource.deleteAllImages()
}