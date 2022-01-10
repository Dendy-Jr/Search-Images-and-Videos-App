package com.dendi.android.search_images_and_videos_app.data.video.cloud


import com.google.gson.annotations.SerializedName

data class SmallCloud(
    @SerializedName("height")
    val height: Int,
    @SerializedName("size")
    val size: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("width")
    val width: Int
)