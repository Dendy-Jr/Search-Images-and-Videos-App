package com.dendi.android.search_images_and_videos_app.feature_videos.domain.usecase

import androidx.paging.PagingData
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.Video
import com.dendi.android.search_images_and_videos_app.feature_videos.domain.VideosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchVideosUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    fun searchVideo(query: String): Flow<PagingData<Video>> = repository.getPagedItems(query)
}