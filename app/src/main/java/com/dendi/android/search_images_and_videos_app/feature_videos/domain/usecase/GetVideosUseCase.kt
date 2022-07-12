package com.dendi.android.search_images_and_videos_app.feature_videos.domain.usecase

import com.dendi.android.search_images_and_videos_app.feature_videos.domain.Video
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.VideosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    operator fun invoke(): Flow<List<Video>> = repository.getItems()
}