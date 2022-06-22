package com.dendi.android.search_images_and_videos_app.domain.image.usecase

import androidx.paging.PagingData
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.domain.image.repository.ImagesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchImageUseCase @Inject constructor(
    private val repository: ImagesRepository,
) {
    fun searchImage(query: String): Flow<PagingData<Image>> = repository.search(query)
}