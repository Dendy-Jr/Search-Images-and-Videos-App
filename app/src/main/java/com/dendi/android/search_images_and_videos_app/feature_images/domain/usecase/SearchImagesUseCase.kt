package com.dendi.android.search_images_and_videos_app.feature_images.domain.usecase

import androidx.paging.PagingData
import com.dendi.android.search_images_and_videos_app.feature_images.domain.Image
import com.dendi.android.search_images_and_videos_app.feature_images.domain.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImagesUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    operator fun invoke(query: String): Flow<PagingData<Image>> = repository.getPagedItems(query)
}