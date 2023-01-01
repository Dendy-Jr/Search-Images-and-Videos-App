package ui.dendi.finder.core.core.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class Video(
    val comments: Int,
    val date: String,
    val downloads: Int,
    val duration: Int,
    val id: Long,
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
) : Parcelable

@Parcelize
data class VideosStreams(
    val large: @RawValue Large,
    val medium: @RawValue Medium,
    val small: @RawValue Small,
    val tiny: @RawValue Tiny,
) : Parcelable

@Parcelize
data class Large(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) : Parcelable

@Parcelize
data class Medium(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) : Parcelable

@Parcelize
data class Small(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) : Parcelable

@Parcelize
data class Tiny(
    val height: Int,
    val size: Int,
    val url: String,
    val width: Int,
) : Parcelable