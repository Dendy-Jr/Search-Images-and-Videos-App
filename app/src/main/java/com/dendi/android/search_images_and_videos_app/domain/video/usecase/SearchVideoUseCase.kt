package com.dendi.android.search_images_and_videos_app.domain.video.usecase

import androidx.paging.PagingData
import com.dendi.android.search_images_and_videos_app.domain.video.Video
import com.dendi.android.search_images_and_videos_app.domain.video.repository.VideosRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchVideoUseCase @Inject constructor(
    private val repository: VideosRepository,
) {
    fun searchVideo(query: String): Flow<PagingData<Video>> = repository.search(query)
}