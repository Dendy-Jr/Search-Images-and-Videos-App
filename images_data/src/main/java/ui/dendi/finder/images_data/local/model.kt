package ui.dendi.finder.images_data.local

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import ui.dendi.finder.core.core.models.Image
import ui.dendi.finder.images_data.local.ImageEntity.Companion.TABLE_IMAGES
import ui.dendi.finder.images_data.local.ImageRemoteKeys.Companion.IMAGE_REMOTE_KEY_TABLE
import java.util.*

@Parcelize
@Entity(tableName = TABLE_IMAGES)
data class ImageEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val localId: Long,
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
    val date: Date,
) : Parcelable {

    fun toDomain() = Image(
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

    companion object {
        const val TABLE_IMAGES = "images"
    }
}

@Entity(tableName = IMAGE_REMOTE_KEY_TABLE)
data class ImageRemoteKeys(
    @PrimaryKey
    val repoId: Long,
    val prevKey: Int?,
    val nextKey: Int?,
) {
    companion object {
        const val IMAGE_REMOTE_KEY_TABLE = "image_remote_key_table"
    }
}