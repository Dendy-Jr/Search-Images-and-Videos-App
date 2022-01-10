package com.dendi.android.search_images_and_videos_app.presentation.core

/**
 * @author Dendy-Jr on 08.01.2022
 * olehvynnytskyi@gmail.com
 */
sealed class UiState {

    data class ResultState<T>(private val item: List<T>) : UiState()

    data class ErrorState(private val message: String) : UiState()

    data class EmptyState(private val query: String) : UiState()

    object InitialIdleState : UiState()

    object LoadingState : UiState()
}
