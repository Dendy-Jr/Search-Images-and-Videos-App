package com.dendi.android.search_images_and_videos_app.presentation.image

import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.dendi.android.search_images_and_videos_app.core.InternetConnection
import com.dendi.android.search_images_and_videos_app.data.image.cache.ImageEntity
import com.dendi.android.search_images_and_videos_app.domain.image.ImagesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * @author Dendy-Jr on 10.12.2021
 * olehvynnytskyi@gmail.com
 */
@ExperimentalPagingApi
class ImageViewModel(
    private val repository: ImagesRepository,
    private val internetConnection: InternetConnection,
    state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(LAST_SEARCH_QUERY, DEFAULT_QUERY)

    var refreshInProgress = false
    var pendingScrollToTopAfterRefresh = false

    var newQueryInProgress = false
    var pendingScrollToTopAfterNewQuery = false

    @ExperimentalPagingApi
    @ExperimentalCoroutinesApi
    val images = currentQuery.asFlow().flatMapLatest { query ->
        repository.getSearchResult(query)
    }.cachedIn(viewModelScope)

    fun searchImage(query: String) {
        currentQuery.value = query
        newQueryInProgress = true
        pendingScrollToTopAfterNewQuery = true
    }

    fun addToFavorite(image: ImageEntity) {
        viewModelScope.launch {
            repository.insertImage(image)
        }
    }

    fun checkConnection() = internetConnection.checkInternetConnection()
    // TODO: 12/30/2021 maybe in the future I will use it

    private companion object {
        const val LAST_SEARCH_QUERY = "current_query"
        const val DEFAULT_QUERY = "animal"
    }
}