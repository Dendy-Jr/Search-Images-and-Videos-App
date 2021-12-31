package com.dendi.android.search_images_and_videos_app.data.video

import com.dendi.android.search_images_and_videos_app.data.video.cache.*
import com.dendi.android.search_images_and_videos_app.data.video.cloud.*

/**
 * @author Dendy-Jr on 28.12.2021
 * olehvynnytskyi@gmail.com
 */
internal fun VideoDto.toVideoEntity() = VideoEntity(
    comments = comments,
    downloads = downloads,
    duration = duration,
    id = id,
    likes = likes,
    pageURL = pageURL,
    pictureId = pictureId,
    tags = tags,
    type = type,
    user = user,
    userId = userId,
    userImageURL = userImageURL,
    videos = videos.toVideosStreamsEntity(),
    views = views,
)

internal fun VideosStreamsDto.toVideosStreamsEntity() = VideosStreamsEntity(
    large = large.toLargeEntity(),
    medium = medium.toMediumEntity(),
    small = small.toSmallEntity(),
    tiny = tiny.toTinyEntity()
)

internal fun LargeDto.toLargeEntity() = LargeEntity(
    height = height,
    size = size,
    url = url,
    width = width
)

internal fun MediumDto.toMediumEntity() = MediumEntity(
    height = height,
    size = size,
    url = url,
    width = width
)

internal fun SmallDto.toSmallEntity() = SmallEntity(
    height = height,
    size = size,
    url = url,
    width = width
)

internal fun TinyDto.toTinyEntity() = TinyEntity(
    height = height,
    size = size,
    url = url,
    width = width
)
