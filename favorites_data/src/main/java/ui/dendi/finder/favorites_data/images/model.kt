package ui.dendi.finder.favorites_data.images

import androidx.room.Entity
import androidx.room.PrimaryKey
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.favorites_data.images.FavoriteImage.Companion.FAVORITE_IMAGE_TABLE

@Entity(tableName = FAVORITE_IMAGE_TABLE)
data class FavoriteImage(
    val collections: Int,
    val comments: Int,
    val date: String,
    val downloads: Int,
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val views: Int,
    val webFormatHeight: Int,
    val webFormatURL: String,
    val webFormatWidth: Int,
) {

    fun toDomain() = Image(
        collections = collections,
        comments = comments,
        date = date,
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

    companion object {
        const val FAVORITE_IMAGE_TABLE = "favorite_image_table"
    }
}