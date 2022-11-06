package ui.dendi.finder.images_data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ui.dendi.finder.core.core.models.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesPagingSource @Inject constructor(
    private val category: String,
    private val colors: String,
    private val orientation: String,
    private val query: String,
    private val remoteDataSource: ImagesRemoteDataSource,
    private val type: String,
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val pageNumber = params.key ?: PAGE_NUMBER
            val pageSize = params.loadSize
            val images = remoteDataSource.getImages(
                query = query,
                page = pageNumber,
                perPage = pageSize,
                type = type,
                category = category,
                orientation = orientation,
                colors = colors
            )

            LoadResult.Page(
                data = images,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (images.isEmpty()) null else pageNumber + 1,
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