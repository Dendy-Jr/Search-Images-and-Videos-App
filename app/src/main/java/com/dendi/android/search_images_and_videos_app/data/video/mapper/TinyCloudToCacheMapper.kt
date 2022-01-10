package com.dendi.android.search_images_and_videos_app.data.video.mapper

import com.dendi.android.search_images_and_videos_app.core.Mapper
import com.dendi.android.search_images_and_videos_app.data.video.cache.TinyCache
import com.dendi.android.search_images_and_videos_app.data.video.cloud.TinyCloud

/**
 * @author Dendy-Jr on 06.01.2022
 * olehvynnytskyi@gmail.com
 */
class TinyCloudToCacheMapper: Mapper<TinyCloud, TinyCache> {
    override suspend fun map(from: TinyCloud)=
        TinyCache(
            height = from.height,
            size = from.size,
            url = from.url,
            width = from.width
        )
}