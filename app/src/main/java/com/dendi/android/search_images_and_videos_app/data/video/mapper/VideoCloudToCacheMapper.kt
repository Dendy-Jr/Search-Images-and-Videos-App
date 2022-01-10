package com.dendi.android.search_images_and_videos_app.data.video.mapper

import com.dendi.android.search_images_and_videos_app.core.Mapper
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoCache
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideoCloud

/**
 * @author Dendy-Jr on 06.01.2022
 * olehvynnytskyi@gmail.com
 */
class VideoCloudToCacheMapper(
    private val mapper: VideosStreamsCloudToCacheMapper
) : Mapper<VideoCloud, VideoCache> {
    override suspend fun map(from: VideoCloud) =
        VideoCache(
            comments = from.comments,
            downloads = from.downloads,
            duration = from.duration,
            id = from.id,
            likes = from.likes,
            pageURL = from.pageURL,
            pictureId = from.pictureId,
            tags = from.tags,
            type = from.type,
            user = from.user,
            userId = from.userId,
            userImageURL = from.userImageURL,
            videos = mapper.map(from.videos),
            views = from.views,
        )
}