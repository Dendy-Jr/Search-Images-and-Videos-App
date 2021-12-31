package com.dendi.android.search_images_and_videos_app.data.image

import android.os.Build
import androidx.annotation.RequiresApi
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImageDto

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */
@RequiresApi(Build.VERSION_CODES.O)
internal fun ImageDto.toEntity(): ImageEntity = ImageEntity(
    collections = this.collections,
    comments = this.comments,
    downloads = this.downloads,
    id = this.id,
    imageHeight = this.imageHeight,
    imageSize = this.imageSize,
    imageWidth = this.imageWidth,
    largeImageURL = this.largeImageURL,
    likes = this.likes,
    pageURL = this.pageURL,
    previewHeight = this.previewHeight,
    previewURL = this.previewURL,
    previewWidth = this.previewWidth,
    tags = this.tags,
    type = this.type,
    user = this.user,
    userId = this.userId,
    userImageURL = this.userImageURL,
    views = this.views,
    webFormatHeight = this.webFormatHeight,
    webFormatURL = this.webFormatURL,
    webFormatWidth = this.webFormatWidth,
    localId = 0,
)



