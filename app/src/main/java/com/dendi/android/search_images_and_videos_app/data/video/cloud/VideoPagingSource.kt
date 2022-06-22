package com.dendi.android.search_images_and_videos_app.data.video.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dendi.android.search_images_and_videos_app.domain.video.Video
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VideoPagingSource @Inject constructor(
    private val remoteDataSource: VideosRemoteDataSource,
    private val query: String,
) : PagingSource<Int, Video>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Video> {
        return try {
            val pageNumber = params.key ?: PAGE_NUMBER
            val pageSize = params.loadSize
            val response = remoteDataSource.getVideos(query, pageNumber, pageSize)
            val videos = response.toDomain()

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

    private fun VideoCloud.toDomain() = Video(
        id = id,
        comments = comments,
        downloads = downloads,
        duration = duration,
        likes = likes,
        pageURL = pageURL,
        pictureId = pictureId,
        tags = tags,
        type = type,
        user = user,
        userId = userId,
        userImageURL = userImageURL,
        videos = videos.toDomain(),
        views = views,
    )

    private fun List<VideoCloud>.toDomain() = map { it.toDomain() }

    private companion object {
        const val PAGE_NUMBER = 1
    }
}