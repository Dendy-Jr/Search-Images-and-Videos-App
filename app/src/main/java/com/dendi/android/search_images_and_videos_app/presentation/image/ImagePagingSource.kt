package com.dendi.android.search_images_and_videos_app.presentation.image

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dendi.android.search_images_and_videos_app.data.core.PixabayDb
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImagesLocalDataSource
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesApi
import com.dendi.android.search_images_and_videos_app.data.image.toEntity
import retrofit2.HttpException

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
class ImagePagingSource(
    private val service: ImagesApi,
    private val query: String,
) : PagingSource<Int, ImageEntity>() {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImageEntity> {
        return try {
            val pageNumber = params.key ?: PAGE_NUMBER
            val pageSize = params.loadSize
            val response = service.searchImages(query, pageNumber, pageSize)
            val images = response.hits.map { it.toEntity() }

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

    override fun getRefreshKey(state: PagingState<Int, ImageEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    private companion object {
        const val PAGE_NUMBER = 1
    }
}