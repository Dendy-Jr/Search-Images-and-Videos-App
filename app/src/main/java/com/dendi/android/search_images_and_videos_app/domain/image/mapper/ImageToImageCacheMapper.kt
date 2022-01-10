package com.dendi.android.search_images_and_videos_app.domain.image.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.dendi.android.search_images_and_videos_app.core.Mapper
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageCache
import com.dendi.android.search_images_and_videos_app.domain.image.Image

/**
 * @author Dendy-Jr on 04.01.2022
 * olehvynnytskyi@gmail.com
 */

class ImageToImageCacheMapper : Mapper<Image, ImageCache> {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun map(from: Image) =
        ImageCache(
            collections = from.collections,
            comments = from.comments,
            downloads = from.downloads,
            id = from.id,
            localId = from.localId,
            imageHeight = from.imageHeight,
            imageSize = from.imageSize,
            imageWidth = from.imageWidth,
            largeImageURL = from.largeImageURL,
            likes = from.likes,
            pageURL = from.pageURL,
            previewHeight = from.previewHeight,
            previewURL = from.previewURL,
            previewWidth = from.previewWidth,
            tags = from.tags,
            type = from.type,
            user = from.user,
            userImageURL = from.userImageURL,
            views = from.views,
            webFormatHeight = from.webFormatHeight,
            webFormatURL = from.webFormatURL,
            webFormatWidth = from.webFormatWidth
        )
}
