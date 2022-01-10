package com.dendi.android.search_images_and_videos_app.data.video.mapper

import com.dendi.android.search_images_and_videos_app.core.Mapper
import com.dendi.android.search_images_and_videos_app.data.video.cache.SmallCache
import com.dendi.android.search_images_and_videos_app.data.video.cloud.SmallCloud

/**
 * @author Dendy-Jr on 06.01.2022
 * olehvynnytskyi@gmail.com
 */
class SmallCloudToCacheMapper: Mapper<SmallCloud, SmallCache> {
    override suspend fun map(from: SmallCloud) =
        SmallCache(
            height = from.height,
            size = from.size,
            url = from.url,
            width = from.width
        )
}