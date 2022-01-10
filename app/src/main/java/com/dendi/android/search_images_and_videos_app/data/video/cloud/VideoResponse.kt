package com.dendi.android.search_images_and_videos_app.data.video.cloud


import com.google.gson.annotations.SerializedName

data class VideoResponse(
    @SerializedName("hits")
    val hits: List<VideoCloud>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)