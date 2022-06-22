package com.dendi.android.search_images_and_videos_app.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dendi.android.search_images_and_videos_app.domain.video.Video
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.InsertVideoUseCase
import com.dendi.android.search_images_and_videos_app.domain.video.usecase.SearchVideoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchVideoViewModel @Inject constructor(
    private val searchVideoUseCase: SearchVideoUseCase,
    private val insertVideoUseCase: InsertVideoUseCase,
    state: SavedStateHandle,
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    val videos = currentQuery.asFlow().flatMapLatest { query ->
        searchVideoUseCase.searchVideo(query)
    }.cachedIn(viewModelScope)

    fun searchVideo(query: String) {
        currentQuery.value = query
    }

    fun addToFavorite(video: Video) {
        viewModelScope.launch {
            insertVideoUseCase.saveVideoToFavorite(video)
        }
    }

    private companion object {
        const val CURRENT_QUERY = "current_query"
        const val DEFAULT_QUERY = "car"
    }
}