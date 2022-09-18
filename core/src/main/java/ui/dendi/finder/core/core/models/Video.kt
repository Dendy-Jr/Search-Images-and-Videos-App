package ui.dendi.finder.core.core.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Video(
    val id: Int,
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
    val videos: VideosStreams,
    val views: Int,
    var isFavorite: Boolean = false,
) : Parcelable

@Parcelize
data class VideosStreams(
    val large: @RawValue Large,
    val medium: @RawValue Medium,
    val small: @RawValue Small,
    val tiny: @RawValue Tiny,
) : Parcelable {

//    fun toDomain() = VideosStreamsCache(
//        large = large.toCache(),
//        medium = medium.toCache(),
//        small = small.toCache(),
//        tiny = tiny.toCache(),
//    )
}

data class Large(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
//    fun toCache() = LargeCache(height = height, size = size, url = url, width = width)
}

data class Medium(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
//    fun toCache() = MediumCache(height = height, size = size, url = url, width = width)
}

data class Small(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
//    fun toCache() = SmallCache(height = height, size = size, url = url, width = width)
}

data class Tiny(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) {
//    fun toCache() = TinyCache(height = height, size = size, url = url, width = width)
}