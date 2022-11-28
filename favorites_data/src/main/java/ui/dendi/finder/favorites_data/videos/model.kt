package ui.dendi.finder.favorites_data.videos

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue
import ui.dendi.finder.core.core.models.*
import ui.dendi.finder.favorites_data.videos.FavoriteVideo.Companion.TABLE_VIDEOS

@Parcelize
@Entity(tableName = TABLE_VIDEOS)
data class FavoriteVideo(
    @PrimaryKey
    val id: Long,
    val comments: Int,
    val downloads: Int,
    val duration: Int,
    val likes: Int,
    val pageURL: String,
    val pictureId: String,
    val tags: String,
    val type: String,
    val user: String,
    val userId: Int,
    val userImageURL: String,
    @Embedded
    val videos: VideosStreamsCache,
    val views: Int,
    var isFavorite: Boolean = false,
) : Parcelable {

    fun toDomain() = Video(
        comments = comments,
        downloads = downloads,
        duration = duration,
        id = id,
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

    companion object {
        const val TABLE_VIDEOS = "videos"
    }
}

@Parcelize
data class VideosStreamsCache(
    val large: @RawValue LargeCache,
    val medium: @RawValue MediumCache,
    val small: @RawValue SmallCache,
    val tiny: @RawValue TinyCache,
) : Parcelable {

    fun toDomain() = VideosStreams(
        large = large.toDomain(),
        medium = medium.toDomain(),
        small = small.toDomain(),
        tiny = tiny.toDomain(),
    )
}

data class LargeCache(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
    fun toDomain() = Large(height = height, size = size, url = url, width = width)
}

data class MediumCache(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
    fun toDomain() = Medium(height = height, size = size, url = url, width = width)
}

data class SmallCache(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
    fun toDomain() = Small(height = height, size = size, url = url, width = width)
}

data class TinyCache(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
    fun toDomain() = Tiny(height = height, size = size, url = url, width = width)
}