package ui.dendi.finder.app.feature_images.data.remote

import com.google.gson.annotations.SerializedName

data class ImagesResponse(
    @SerializedName("hits")
    val hits: List<ImageResponseItem>,
    @SerializedName("total")
    val total: Int,
    @SerializedName("totalHits")
    val totalHits: Int,
)

data class ImageResponseItem(
    @SerializedName("collections")
    val collections: Int,
    @SerializedName("comments")
    val comments: Int,
    @SerializedName("downloads")
    val downloads: Int,
    @SerializedName("id")
    val id: Long,
    @SerializedName("imageHeight")
    val imageHeight: Int,
    @SerializedName("imageSize")
    val imageSize: Int,
    @SerializedName("imageWidth")
    val imageWidth: Int,
    @SerializedName("largeImageURL")
    val largeImageURL: String,
    @SerializedName("likes")
    val likes: Int,
    @SerializedName("pageURL")
    val pageURL: String,
    @SerializedName("previewHeight")
    val previewHeight: Int,
    @SerializedName("previewURL")
    val previewURL: String,
    @SerializedName("previewWidth")
    val previewWidth: Int,
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
    @SerializedName("views")
    val views: Int,
    @SerializedName("webformatHeight")
    val webFormatHeight: Int,
    @SerializedName("webformatURL")
    val webFormatURL: String,
    @SerializedName("webformatWidth")
    val webFormatWidth: Int,
)