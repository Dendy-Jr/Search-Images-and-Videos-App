package ui.dendi.finder.core.core.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.OffsetDateTime
import java.util.*

@Parcelize
data class Image(
    val collections: Int,
    val comments: Int,
    val downloads: Int,
    val id: Long,
    val localId: Long,
    val imageHeight: Int,
    val imageSize: Int,
    val imageWidth: Int,
    val largeImageURL: String,
    val likes: Int,
    val pageURL: String,
    val previewHeight: Int,
    val previewURL: String,
    val previewWidth: Int,
    val tags: String,
    val type: String,
    val user: String,
    val userImageURL: String,
    val views: Int,
    val webFormatHeight: Int,
    val webFormatURL: String,
    val webFormatWidth: Int,
    var isFavorite: Boolean = false,
    val off: OffsetDateTime? = OffsetDateTime.now(),
) : Parcelable {

//    fun toCache() = ImageEntity(
//        collections = collections,
//        comments = comments,
//        downloads = downloads,
//        id = id,
//        localId = id,
//        imageHeight = imageHeight,
//        imageSize = imageSize,
//        imageWidth = imageWidth,
//        largeImageURL = largeImageURL,
//        likes = likes,
//        pageURL = pageURL,
//        previewWidth = previewWidth,
//        previewHeight = previewHeight,
//        previewURL = previewURL,
//        tags = tags,
//        type = type,
//        user = user,
//        userImageURL = userImageURL,
//        views = views,
//        webFormatHeight = webFormatHeight,
//        webFormatURL = webFormatURL,
//        webFormatWidth = webFormatWidth,
//        date = Calendar.getInstance().time,
//    )
//
//    fun toFavorite() = FavoriteImage(
//        id = id,
//        image = ImageEntity(
//            collections = collections,
//            comments = comments,
//            downloads = downloads,
//            id = id,
//            localId = id,
//            imageHeight = imageHeight,
//            imageSize = imageSize,
//            imageWidth = imageWidth,
//            largeImageURL = largeImageURL,
//            likes = likes,
//            pageURL = pageURL,
//            previewWidth = previewWidth,
//            previewHeight = previewHeight,
//            previewURL = previewURL,
//            tags = tags,
//            type = type,
//            user = user,
//            userImageURL = userImageURL,
//            views = views,
//            webFormatHeight = webFormatHeight,
//            webFormatURL = webFormatURL,
//            webFormatWidth = webFormatWidth,
//            date = Calendar.getInstance().time,
//        )
//    )
}