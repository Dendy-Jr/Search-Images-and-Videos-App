package com.dendi.android.search_images_and_videos_app.data.video.cloud


import com.google.gson.annotations.SerializedName

data class VideosStreamsCloud(
    @SerializedName("large")
    val large: LargeCloud,
    @SerializedName("medium")
    val medium: MediumCloud,
    @SerializedName("small")
    val small: SmallCloud,
    @SerializedName("tiny")
    val tiny: TinyCloud
)