package com.dendi.android.search_images_and_videos_app.domain.image

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import kotlinx.android.parcel.Parcelize
import java.time.OffsetDateTime

/**
 * @author Dendy-Jr on 04.01.2022
 * olehvynnytskyi@gmail.com
 */
@Parcelize
data class Image @RequiresApi(Build.VERSION_CODES.O) constructor(
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
    val off: OffsetDateTime? = OffsetDateTime.now()
) : Parcelable {

    companion object {
        @RequiresApi(Build.VERSION_CODES.O)
        val empty = Image(
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            0,
            "",
            0,
            "",
            0,
            "",
            0,
            "",
            "",
            "",
            "",
            0,
            0,
            "",
            0,
        )
    }
}