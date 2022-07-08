package com.dendi.android.search_images_and_videos_app.feature_images.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.dendi.android.search_images_and_videos_app.core.db.PixabayDb
import com.dendi.android.search_images_and_videos_app.feature_images.data.remote.ImagesRemoteDataSource
import okio.IOException
import retrofit2.HttpException
import timber.log.Timber

private const val IMAGE_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class ImagesRemoteMediator(
    private val remoteDataSource: ImagesRemoteDataSource,
    private val query: String,
    private val database: PixabayDb,
) : RemoteMediator<Int, ImageCache>() {

    private val imageDao = database.imageDao()
    private val imageRemoteKeyDao = database.imageRemoteKeysDao()

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageCache>,
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                    remoteKeys?.nextKey?.minus(1) ?: IMAGE_STARTING_PAGE_INDEX
                }
                LoadType.PREPEND -> {
                    val remoteKeys = getRemoteKeyForFirstItem(state)
                    val prevKey = remoteKeys?.prevKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    prevKey
                }
                LoadType.APPEND -> {
                    val remoteKeys = getRemoteKeyForLastItem(state)
                    val nextKey = remoteKeys?.nextKey
                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    nextKey
                }
            }

            val images = remoteDataSource.getImages(query, page, state.config.pageSize)
            val endPaginationReached = images.isEmpty()
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    imageRemoteKeyDao.clearRemoteKeys()
                    imageDao.clearAll()
                }
                val prevKey = if (page == IMAGE_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endPaginationReached) null else page + 1
                val keys = images.map {
                    ImageRemoteKeys(
                        repoId = it.id,
                        prevKey = prevKey,
                        nextKey = nextKey
                    )
                }
                Timber.d("images size:${images.size}, prevKey:$prevKey, nextKey:$nextKey, state.config.pageSize:${state.config.pageSize}, keys.size:${keys.size}")
                imageRemoteKeyDao.insertAll(keys)
                imageDao.insertAll(images.map {
                    it.toCache()
                })
            }
            MediatorResult.Success(endOfPaginationReached = endPaginationReached)
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ImageCache>): ImageRemoteKeys? {
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { image ->
                imageRemoteKeyDao.remoteKeysById(image.localId)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ImageCache>): ImageRemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { image ->
                imageRemoteKeyDao.remoteKeysById(image.localId)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ImageCache>): ImageRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.localId?.let { imageId ->
                imageRemoteKeyDao.remoteKeysById(imageId)

            }
        }
    }
}