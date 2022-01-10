package com.dendi.android.search_images_and_videos_app.data.video.cloud


import com.google.gson.annotations.SerializedName

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
    val views: Int
)