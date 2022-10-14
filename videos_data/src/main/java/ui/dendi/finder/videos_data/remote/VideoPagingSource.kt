package ui.dendi.finder.videos_data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import retrofit2.HttpException
import ui.dendi.finder.core.core.models.Video
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoPagingSource @Inject constructor(
    private val remoteDataSource: VideosRemoteDataSource,
    private val query: String,
    private val type: String?,
    private val category: String?,
    private val order: String?,
) : PagingSource<Int, Video>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        return try {
            val pageNumber = params.key ?: PAGE_NUMBER
            val pageSize = params.loadSize
            val videos =
                remoteDataSource.getVideos(query, pageNumber, pageSize, type, category, order)

            LoadResult.Page(
                data = videos,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (videos.isEmpty()) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Video>): Int {
        return 1
    }

    private companion object {
        const val PAGE_NUMBER = 1
    }
}