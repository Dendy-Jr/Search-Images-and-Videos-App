package ui.dendi.finder.app.feature_images.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ui.dendi.finder.app.feature_images.data.local.ImagesLocalDataSource
import ui.dendi.finder.app.feature_images.domain.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesPagingSource @Inject constructor(
    private val remoteDataSource: ImagesRemoteDataSource,
    private val localDataSource: ImagesLocalDataSource,
    private val query: String,
    private val type: String,
    private val category: String,
    private val orientation: String,
    private val colors: String,
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val pageNumber = params.key ?: PAGE_NUMBER
            val pageSize = params.loadSize
            val items = remoteDataSource.getImages(
                query,
                pageNumber,
                pageSize,
                type,
                category,
                orientation,
                colors
            )

            localDataSource.insertAllImages(items)

            LoadResult.Page(
                data = items,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (items.isEmpty()) null else pageNumber + 1,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Image>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val PAGE_NUMBER = 1
    }
}