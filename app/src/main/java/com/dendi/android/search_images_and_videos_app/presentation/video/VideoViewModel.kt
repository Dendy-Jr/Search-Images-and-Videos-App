package com.dendi.android.search_images_and_videos_app.presentation.video

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.dendi.android.search_images_and_videos_app.data.video.cache.VideoEntity
import com.dendi.android.search_images_and_videos_app.domain.video.VideosRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

/**
 * @author Dendy-Jr on 12.12.2021
 * olehvynnytskyi@gmail.com
 */
class VideoViewModel(
    private val repository: VideosRepository,
    state: SavedStateHandle
) : ViewModel() {

    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    @ExperimentalCoroutinesApi
    val videos = currentQuery.asFlow().flatMapLatest { query ->
        repository.getSearchResult(query)
    }.cachedIn(viewModelScope)

    fun searchVideo(query: String) {
        currentQuery.value = query
    }

    fun addToFavorite(video: VideoEntity) {
        viewModelScope.launch {
            repository.insertVideo(video)
        }
    }

    private companion object {
        const val CURRENT_QUERY = "current_query"
        const val DEFAULT_QUERY = "car"
    }
}