package com.dendi.android.search_images_and_videos_app.data.video.cloud

import com.dendi.android.search_images_and_videos_app.domain.video.*
import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("hits")
    val hits: List<VideoCloud>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int,
)

data class VideoCloud(
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("pageURL")
    val pageURL: String,
    @SerializedName("picture_id")
    val pictureId: String,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("user")
    val user: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("userImageURL")
    val userImageURL: String,
    @SerializedName("videos")
    val videos: VideosStreamsCloud,
    @SerializedName("views")
    val views: Int,
)

data class VideosStreamsCloud(
    @SerializedName("large")
    val large: LargeCloud,
    @SerializedName("medium")
    val medium: MediumCloud,
    @SerializedName("small")
    val small: SmallCloud,
    @SerializedName("tiny")
    val tiny: TinyCloud
) {
    fun toDomain() = VideosStreams(
        large = large.toDomain(),
        medium = medium.toDomain(),
        small = small.toDomain(),
        tiny = tiny.toDomain(),
    )
}

data class LargeCloud(
    @SerializedName("height")
    val height: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
) {
    fun toDomain() = Large(height = height, size = size, url = url, width = width)

}

data class MediumCloud(
    @SerializedName("height")
    val height: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
) {
    fun toDomain() = Medium(height = height, size = size, url = url, width = width)
}

data class SmallCloud(
    @SerializedName("height")
    val height: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
) {
    fun toDomain() = Small(height = height, size = size, url = url, width = width)
}

data class TinyCloud(
    @SerializedName("height")
    val height: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int,
) {
    fun toDomain() = Tiny(height = height, size = size, url = url, width = width)
}