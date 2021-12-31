package com.dendi.android.search_images_and_videos_app.data.video.cloud


import com.google.gson.annotations.SerializedName

data class VideosStreamsDto(
    @SerializedName("large")
    val large: LargeDto,
    @SerializedName("medium")
    val medium: MediumDto,
    @SerializedName("small")
    val small: SmallDto,
    @SerializedName("tiny")
    val tiny: TinyDto
)