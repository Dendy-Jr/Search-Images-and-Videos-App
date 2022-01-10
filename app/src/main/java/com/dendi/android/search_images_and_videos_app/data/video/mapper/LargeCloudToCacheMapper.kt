package com.dendi.android.search_images_and_videos_app.data.video.mapper

import com.dendi.android.search_images_and_videos_app.core.Mapper
import com.dendi.android.search_images_and_videos_app.data.video.cache.LargeCache
import com.dendi.android.search_images_and_videos_app.data.video.cloud.LargeCloud

/**
 * @author Dendy-Jr on 06.01.2022
 * olehvynnytskyi@gmail.com
 */
class LargeCloudToCacheMapper: Mapper<LargeCloud, LargeCache> {
    override suspend fun map(from: LargeCloud) =
        LargeCache(
            height = from.height,
            size = from.size,
            url = from.url,
            width = from.width
        )
}