package ui.dendi.finder.app.feature_images.data.remote

import ui.dendi.finder.app.feature_images.domain.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRemoteDataSource @Inject constructor(
    private val imagesApi: ImagesApi,
) {
    suspend fun getImages(query: String, page: Int, perPage: Int, type: String, category: String): List<Image> =
        imagesApi.searchImages(query, page, perPage, type, category).hits.toDomain()

    private fun ImageResponseItem.toDomain() = Image(
        collections = collections,
        comments = comments,
        downloads = downloads,
        id = id,
        localId = id,
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

    private fun List<ImageResponseItem>.toDomain() = map { it.toDomain() }
}