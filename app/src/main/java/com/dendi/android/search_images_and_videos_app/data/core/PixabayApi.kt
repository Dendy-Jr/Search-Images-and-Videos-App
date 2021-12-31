package com.dendi.android.search_images_and_videos_app.data.core

import com.dendi.android.search_images_and_videos_app.data.image.cloud.FileApi
import com.dendi.android.search_images_and_videos_app.data.image.cloud.ImagesApi
import com.dendi.android.search_images_and_videos_app.data.video.cloud.VideosApi

/**
 * @author Dendy-Jr on 13.12.2021
 * olehvynnytskyi@gmail.com
 */
interface PixabayApi: ImagesApi, VideosApi, FileApi