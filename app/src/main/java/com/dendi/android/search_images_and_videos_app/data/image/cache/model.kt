package com.dendi.android.search_images_and_videos_app.data.image.cache

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dendi.android.search_images_and_videos_app.data.image.cache.FavoriteImage.Companion.FAVORITE_IMAGE_TABLE
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageCache.Companion.TABLE_IMAGES
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageRemoteKeys.Companion.IMAGE_REMOTE_KEY_TABLE
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import kotlinx.android.parcel.Parcelize
import java.time.OffsetDateTime

@Entity(tableName = FAVORITE_IMAGE_TABLE)
data class FavoriteImage(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @Embedded(prefix = "Image_")
    val image: ImageCache,
) {
    companion object {
        const val FAVORITE_IMAGE_TABLE = "favorite_image_table"
    }
}

@Parcelize
@Entity(tableName = TABLE_IMAGES)
data class ImageCache(
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
    var isFavorite: Boolean = false,
    val date: OffsetDateTime? = OffsetDateTime.now()
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