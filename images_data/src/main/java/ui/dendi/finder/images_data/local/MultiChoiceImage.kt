package ui.dendi.finder.images_data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.images_data.local.MultiChoiceImage.Companion.MULTI_CHOICE_IMAGE

@Entity(tableName = MULTI_CHOICE_IMAGE)
data class MultiChoiceImage(
    val collections: Int,
    val comments: Int,
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
        downloads = downloads,
        date = "",
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
        const val MULTI_CHOICE_IMAGE = "MULTI_CHOICE_IMAGE"
    }
}