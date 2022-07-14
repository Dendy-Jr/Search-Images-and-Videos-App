package ui.dendi.android.finder.core.util

sealed class UiState<T> {

    data class ResultState<T>(private val item: List<T>) : UiState<T>()

    data class ErrorState(private val message: String) : UiState<Nothing>()

    data class EmptyState(private val query: String) : UiState<Nothing>()

    object InitialIdleState : UiState<Nothing>()

    object LoadingState : UiState<Nothing>()
}

//TODO maybe delete it
