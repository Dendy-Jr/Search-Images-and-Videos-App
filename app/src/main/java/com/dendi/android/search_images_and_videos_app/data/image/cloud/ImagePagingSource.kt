package com.dendi.android.search_images_and_videos_app.data.image.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagePagingSource @Inject constructor(
    private val remoteDataSource: ImagesRemoteDataSource,
    private val query: String,
) : PagingSource<Int, Image>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Image> {
        return try {
            val pageNumber = params.key ?: PAGE_NUMBER
            val pageSize = params.loadSize
            val response = remoteDataSource.getImages(query, pageNumber, pageSize)
            val images = response.toDomains()

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

    private fun ImageResponseItem.toDomain() = Image(
        collections = collections,
        comments = comments,
        downloads = downloads,
        id = id,
        localId = System.currentTimeMillis(),
        imageHeight = imageHeight,
        imageSize = imageSize,
        imageWidth = imageWidth,
        largeImageURL = largeImageURL,
        likes = likes,
        pageURL = pageURL,
        previewHeight = previewHeight,
        previewURL = previewURL,
        previewWidth = previewWidth,
        tags = tags,
        type = type,
        user = user,
        userImageURL = userImageURL,
        views = views,
        webFormatHeight = webFormatHeight,
        webFormatURL = webFormatURL,
        webFormatWidth = webFormatWidth,
    )

    private fun List<ImageResponseItem>.toDomains(): List<Image> = map { it.toDomain() }

    private companion object {
        const val PAGE_NUMBER = 1
    }
}