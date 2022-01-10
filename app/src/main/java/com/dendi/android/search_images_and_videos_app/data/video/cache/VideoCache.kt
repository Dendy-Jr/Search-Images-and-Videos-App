package com.dendi.android.search_images_and_videos_app.data.video.cache

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache.Companion.TABLE_VIDEOS
import kotlinx.android.parcel.Parcelize

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@Parcelize
@Entity(tableName = TABLE_VIDEOS)
data class VideoCache(

    @ColumnInfo(name = COMMENTS)
    val comments: Int,

    @ColumnInfo(name = DOWNLOADS)
    val downloads: Int,

    @ColumnInfo(name = DURATION)
    val duration: Int,

    @PrimaryKey
    @ColumnInfo(name = ID)
    val id: Int,

    @ColumnInfo(name = LIKES)
    val likes: Int,

    @ColumnInfo(name = PAGE_URL)
    val pageURL: String,

    @ColumnInfo(name = PICTURE_ID)
    val pictureId: String,

    @ColumnInfo(name = TAGS)
    val tags: String,

    @ColumnInfo(name = TYPE)
    val type: String,

    @ColumnInfo(name = USER)
    val user: String,

    @ColumnInfo(name = USER_ID)
    val userId: Int,

    @ColumnInfo(name = USER_IMAGE_URL)
    val userImageURL: String,

    @Embedded
    val videos: VideosStreamsCache,

    @ColumnInfo(name = VIEWS)
    val views: Int,

    @ColumnInfo(name = FAVORITE)
    var isFavorite: Boolean = false
) : Parcelable {
    companion object {
        const val TABLE_VIDEOS = "videos"

        const val COMMENTS = "comments"
        const val DOWNLOADS = "downloads"
        const val DURATION = "duration"
        const val ID = "id"
        const val LIKES = "likes"
        const val PAGE_URL = "pageURL"
        const val PICTURE_ID = "picture_id"
        const val TAGS = "tags"
        const val TYPE = "type"
        const val USER = "user"
        const val USER_ID = "user_id"
        const val USER_IMAGE_URL = "userImageURL"
        const val VIEWS = "views"
        const val FAVORITE = "favorite"
    }
}
