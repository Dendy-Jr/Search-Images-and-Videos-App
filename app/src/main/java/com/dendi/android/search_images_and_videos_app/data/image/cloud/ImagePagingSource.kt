package com.dendi.android.search_images_and_videos_app.data.image.cloud

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dendi.android.search_images_and_videos_app.core.forList
import com.dendi.android.search_images_and_videos_app.data.image.mapper.ImageCloudToImageMapper
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import retrofit2.HttpException

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
class ImagePagingSource(
    private val remoteDataSource: ImagesRemoteDataSource,
    private val query: String,
    private val mapper: ImageCloudToImageMapper
) : PagingSource<Int, Image>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val pageNumber = params.key ?: PAGE_NUMBER
            val pageSize = params.loadSize
            val response = remoteDataSource.getImages(query, pageNumber, pageSize)
            val images = mapper.forList().invoke(response)

            LoadResult.Page(
                data = images,
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (images.isEmpty()) null else pageNumber + 1
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