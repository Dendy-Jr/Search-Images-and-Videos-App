package com.dendi.android.search_images_and_videos_app.data.video.mapper

import com.dendi.android.search_images_and_videos_app.core.Mapper
import com.dendi.android.search_images_and_videos_app.data.video.cache.MediumCache
import com.dendi.android.search_images_and_videos_app.data.video.cloud.MediumCloud

/**
 * @author Dendy-Jr on 06.01.2022
 * olehvynnytskyi@gmail.com
 */
class MediumCloudToCacheMapper: Mapper<MediumCloud, MediumCache> {
    override suspend fun map(from: MediumCloud) =
        MediumCache(
            height = from.height,
            size = from.size,
            url = from.url,
            width = from.width
        )
}