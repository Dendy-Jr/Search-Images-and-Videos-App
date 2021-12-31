package com.dendi.android.search_images_and_videos_app.presentation.video

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoEntity
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideosApi
import com.dendi.android.search_images_and_videos_app.data.video.toVideoEntity


/**
 * @author Dendy-Jr on 11.12.2021
 * olehvynnytskyi@gmail.com
 */
class VideoPagingSource(
    private val service: VideosApi,
    private val query: String
) : PagingSource<Int, VideoEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, VideoEntity> {
        return try {
            val pageNumber = params.key ?: PAGE_NUMBER
            val pageSize = params.loadSize
            val response = service.searchVideo(query, pageNumber, pageSize)
            LoadResult.Page(
                data = response.hits.map { it.toVideoEntity() },
                prevKey = if (pageNumber > 1) pageNumber - 1 else null,
                nextKey = if (pageNumber * 20 > response.totalHits) null else pageNumber + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, VideoEntity>): Int {
        return 1
    }

    private companion object {
        const val PAGE_NUMBER = 1
    }
}