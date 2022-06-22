package com.dendi.android.search_images_and_videos_app.presentation.viewmodel

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.dendi.android.search_images_and_videos_app.core.CheckInternetConnection
import com.dendi.android.search_images_and_videos_app.domain.image.Image
import com.dendi.android.search_images_and_videos_app.presentation.ImagesUIState
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.GetImagesUseCase
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.InsertImageUseCase
import com.dendi.android.search_images_and_videos_app.domain.image.usecase.SearchImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchImageViewModel @Inject constructor(
    private val searchImageUseCase: SearchImageUseCase,
    private val insertImageUseCase: InsertImageUseCase,
    private val getImagesUseCase: GetImagesUseCase,
    private val internetConnection: CheckInternetConnection,
    state: SavedStateHandle,
) : ViewModel() {

    private val currentQuery = state.getLiveData(LAST_SEARCH_QUERY, DEFAULT_QUERY)

    var refreshInProgress = false
    var pendingScrollToTopAfterRefresh = false

    var newQueryInProgress = false

    val images = currentQuery.asFlow().flatMapLatest { query ->
        searchImageUseCase.searchImage(query)
    }.cachedIn(viewModelScope)

    val loadImage: Flow<ImagesUIState> = flow {
        if (internetConnection.checkInternetConnection()) {
            emit(ImagesUIState.LoadDataFromInternet(images))
        } else {
            emit(ImagesUIState.GetDataWithoutInternet(getImage()))
        }
    }

    fun getImage() = getImagesUseCase.getImages()

    fun searchImage(query: String) {
        currentQuery.value = query
        newQueryInProgress = true
    }

    fun addToFavorite(image: Image) {
        viewModelScope.launch {
            insertImageUseCase.saveImageToFavorites(image)
        }
    }

    private companion object {
        const val LAST_SEARCH_QUERY = "current_query"
        const val DEFAULT_QUERY = "animal"
    }
}