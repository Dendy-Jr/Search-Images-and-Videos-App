package com.dendi.android.search_images_and_videos_app.presentation

import androidx.paging.PagingData
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import kotlinx.coroutines.flow.Flow

sealed class ImagesUIState {

    data class LoadDataFromInternet(private val data: Flow<PagingData<Image>>) : ImagesUIState()

    data class GetDataWithoutInternet(private val data: Flow<List<Image>>) : ImagesUIState()
}