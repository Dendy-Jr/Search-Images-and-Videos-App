package com.dendi.android.search_images_and_videos_app.data.image.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.dendi.android.search_images_and_videos_app.core.Mapper
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageCache
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImageCloud

/**
 * @author Dendy-Jr on 20.12.2021
 * olehvynnytskyi@gmail.com
 */

class ImageCloudToCacheMapper : Mapper<ImageCloud, ImageCache> {
    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun map(from: ImageCloud) =
        ImageCache(
            collections = from.collections,
            comments = from.comments,
            downloads = from.downloads,
            id = from.id,
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
            webFormatWidth = from.webFormatWidth,
            localId = 0,
        )
}




