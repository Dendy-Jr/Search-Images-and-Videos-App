package com.dendi.android.search_images_and_videos_app.data.video.mapper

import com.dendi.android.search_images_and_videos_app.core.Mapper
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideosStreamsCache
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideosStreamsCloud

/**
 * @author Dendy-Jr on 06.01.2022
 * olehvynnytskyi@gmail.com
 */
class VideosStreamsCloudToCacheMapper(
    private val largeMapper: LargeCloudToCacheMapper,
    private val mediumMapper: MediumCloudToCacheMapper,
    private val smallMapper: SmallCloudToCacheMapper,
    private val tinyMapper: TinyCloudToCacheMapper,
): Mapper<VideosStreamsCloud, VideosStreamsCache> {
    override suspend fun map(from: VideosStreamsCloud) =
        VideosStreamsCache(
            large = largeMapper.map(from.large),
            medium = mediumMapper.map(from.medium),
            small = smallMapper.map(from.small),
            tiny = tinyMapper.map(from.tiny)
        )
}