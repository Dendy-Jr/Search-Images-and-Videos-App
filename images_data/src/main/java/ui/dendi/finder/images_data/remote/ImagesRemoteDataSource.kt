package ui.dendi.finder.images_data.remote

import ui.dendi.finder.core.core.models.Image
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImagesRemoteDataSource @Inject constructor(
    private val imagesApi: ImagesApi,
) {
    suspend fun getImages(
        category: String,
        colors: String,
        orientation: String,
        page: Int,
        perPage: Int,
        query: String,
        type: String,
    ): List<Image> =
        imagesApi.searchImages(
            query,
            page,
            perPage,
            type,
            category,
            orientation,
            colors,
        ).hits.toDomain()

    private fun ImageResponseItem.toDomain() = Image(
        collections = collections,
        comments = comments,
        downloads = downloads,
        id = id,
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