package com.dendi.android.search_images_and_videos_app.data.image.cloud


import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("hits")
    val hits: List<ImageCloud>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int
)