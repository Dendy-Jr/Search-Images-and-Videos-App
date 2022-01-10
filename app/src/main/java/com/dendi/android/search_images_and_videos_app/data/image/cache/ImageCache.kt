package com.dendi.android.search_images_and_videos_app.data.image.cache

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageCache.Companion.TABLE_IMAGES
import kotlinx.android.parcel.Parcelize
import java.time.OffsetDateTime


/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Parcelize
@Entity(tableName = TABLE_IMAGES)
data class ImageCache @RequiresApi(Build.VERSION_CODES.O) constructor(
    @ColumnInfo(name = COLLECTIONS)
    val collections: Int,

    @ColumnInfo(name = COMMENTS)
    val comments: Int,

    @ColumnInfo(name = DOWNLOADS)
    val downloads: Int,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = ID)
    val id: Long,

    @ColumnInfo(name = REMOTE_ID)
    val localId: Long,

    @ColumnInfo(name = IMAGE_HEIGHT)
    val imageHeight: Int,

    @ColumnInfo(name = IMAGE_SIZE)
    val imageSize: Int,

    @ColumnInfo(name = IMAGE_WIDTH)
    val imageWidth: Int,

    @ColumnInfo(name = LARGE_IMAGE_URL)
    val largeImageURL: String,

    @ColumnInfo(name = LIKES)
    val likes: Int,

    @ColumnInfo(name = PAGE_URL)
    val pageURL: String,

    @ColumnInfo(name = PREVIEW_HEIGHT)
    val previewHeight: Int,

    @ColumnInfo(name = PREVIEW_URL)
    val previewURL: String,

    @ColumnInfo(name = PREVIEW_WIDTH)
    val previewWidth: Int,

    @ColumnInfo(name = TAGS)
    val tags: String,

    @ColumnInfo(name = TYPE)
    val type: String,

    @ColumnInfo(name = USER)
    val user: String,

    @ColumnInfo(name = USER_IMAGE_URL)
    val userImageURL: String,

    @ColumnInfo(name = VIEWS)
    val views: Int,

    @ColumnInfo(name = WEB_FORMAT_HEIGHT)
    val webFormatHeight: Int,

    @ColumnInfo(name = WEB_FORMAT_URL)
    val webFormatURL: String,

    @ColumnInfo(name = WEB_FORMAT_WIDTH)
    val webFormatWidth: Int,

    @ColumnInfo(name = FAVORITE)
    var isFavorite: Boolean = false,

    @ColumnInfo(name = DateTime)
    val off: OffsetDateTime? = OffsetDateTime.now()
): Parcelable {
    companion object {
        const val TABLE_IMAGES = "images"
        const val COLLECTIONS = "collections"
        const val COMMENTS = "comments"
        const val DOWNLOADS = "downloads"
        const val ID = "id"
        const val REMOTE_ID = "remote_id"
        const val IMAGE_HEIGHT = "image_height"
        const val IMAGE_SIZE = "image_size"
        const val IMAGE_WIDTH = "image_width"
        const val LARGE_IMAGE_URL = "large_image_url"
        const val LIKES = "likes"
        const val PAGE_URL = "page_url"
        const val PREVIEW_HEIGHT = "preview_height"
        const val PREVIEW_URL = "preview_url"
        const val PREVIEW_WIDTH = "preview_width"
        const val TAGS = "tags"
        const val TYPE = "type"
        const val USER = "user"
        const val USER_IMAGE_URL = "user_image_url"
        const val VIEWS = "views"
        const val WEB_FORMAT_HEIGHT = "web_format_height"
        const val WEB_FORMAT_URL = "web_format_url"
        const val WEB_FORMAT_WIDTH = "web_format_width"
        const val FAVORITE = "favorite"
        const val DateTime = "data_time"
    }
}