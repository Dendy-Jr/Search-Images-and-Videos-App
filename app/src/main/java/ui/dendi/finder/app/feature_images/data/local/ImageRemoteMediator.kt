package ui.dendi.finder.app.feature_images.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import timber.log.Timber
import ui.dendi.finder.app.feature_images.data.remote.ImagesRemoteDataSource

//TODO Maybe use in future

private const val IMAGE_STARTING_PAGE_INDEX = 1

@ExperimentalPagingApi
class ImagesRemoteMediator(
    private val remoteDataSource: ImagesRemoteDataSource,
    private val query: String,
    private val imageDao: ImageDao,
    private val type: String,
    private val category: String,
    private val orientation: String,
    private val colors: String,
) : RemoteMediator<Int, ImageEntity>() {

    private var pageIndex = 1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, ImageEntity>
    ): MediatorResult {
        pageIndex =
            getPageIndex(loadType) ?: return MediatorResult.Success(endOfPaginationReached = true)

        val limit = state.config.pageSize
        val offset = pageIndex

        return try {
            val images = getImages(query, offset, limit)
            Timber.d("limit: $limit, offset: $offset")
            if (loadType == LoadType.REFRESH) {
                imageDao.refresh(images)
            } else {
                imageDao.insertAll(images)
            }
            MediatorResult.Success(
                endOfPaginationReached = images.size < limit
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

    private suspend fun getImages(query: String, offset: Int, limit: Int): List<ImageEntity> {
        return remoteDataSource.getImages(query, offset, limit, type, category, orientation, colors)
            .map { it.toCache() }
    }

    private fun getPageIndex(loadType: LoadType): Int? {
        pageIndex = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return null
            LoadType.APPEND -> ++pageIndex
        }
        Timber.d(loadType.name)
        return pageIndex
    }

//    private val imageDao = database.imageDao()
//    private val imageRemoteKeyDao = database.imageRemoteKeysDao()
//
//    override suspend fun initialize(): InitializeAction {
//        return InitializeAction.LAUNCH_INITIAL_REFRESH
//    }
//
//    override suspend fun load(
//        loadType: LoadType,
//        state: PagingState<Int, ImageEntity>,
//    ): MediatorResult {
//        return try {
//            val page = when (loadType) {
//                LoadType.REFRESH -> {
//                    val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
//                    remoteKeys?.nextKey?.minus(1) ?: IMAGE_STARTING_PAGE_INDEX
//                }
//                LoadType.PREPEND -> {
//                    val remoteKeys = getRemoteKeyForFirstItem(state)
//                    val prevKey = remoteKeys?.prevKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                    prevKey
//                }
//                LoadType.APPEND -> {
//                    val remoteKeys = getRemoteKeyForLastItem(state)
//                    val nextKey = remoteKeys?.nextKey
//                        ?: return MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
//                    nextKey
//                }
//            }
//
//            val images = remoteDataSource.getImages(query, page, state.config.pageSize)
//            val endPaginationReached = images.isEmpty()
//            database.withTransaction {
//                if (loadType == LoadType.REFRESH) {
//                    imageRemoteKeyDao.clearRemoteKeys()
//                    imageDao.clearAll()
//                }
//                val prevKey = if (page == IMAGE_STARTING_PAGE_INDEX) null else page - 1
//                val nextKey = if (endPaginationReached) null else page + 1
//                val keys = images.map {
//                    ImageRemoteKeys(
//                        repoId = it.id,
//                        prevKey = prevKey,
//                        nextKey = nextKey
//                    )
//                }
//                Timber.d("images size:${images.size}, prevKey:$prevKey, nextKey:$nextKey, state.config.pageSize:${state.config.pageSize}, keys.size:${keys.size}")
//                imageRemoteKeyDao.insertAll(keys)
//                imageDao.insertAll(images.map {
//                    it.toCache()
//                })
//            }
//            MediatorResult.Success(endOfPaginationReached = endPaginationReached)
//        } catch (e: IOException) {
//            MediatorResult.Error(e)
//        } catch (e: HttpException) {
//            MediatorResult.Error(e)
//        }
//    }
//
//    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ImageEntity>): ImageRemoteKeys? {
//        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
//            ?.let { image ->
//                imageRemoteKeyDao.remoteKeysById(image.localId)
//            }
//    }
//
//    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ImageEntity>): ImageRemoteKeys? {
//        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
//            ?.let { image ->
//                imageRemoteKeyDao.remoteKeysById(image.localId)
//            }
//    }
//
//    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, ImageEntity>): ImageRemoteKeys? {
//        return state.anchorPosition?.let { position ->
//            state.closestItemToPosition(position)?.localId?.let { imageId ->
//                imageRemoteKeyDao.remoteKeysById(imageId)
//
//            }
//        }
//    }
}