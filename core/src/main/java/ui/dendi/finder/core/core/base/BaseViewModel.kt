package ui.dendi.finder.core.core.base

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ui.dendi.finder.core.core.Logger
import ui.dendi.finder.core.core.navigation.BackNavDirections
import javax.inject.Inject

interface ViewModelOwner<VM : BaseViewModel> {
    val viewModel: VM
}

open class BaseViewModel(private val logger: Logger) : ViewModel(), Logger by logger {

    private val _navigation = MutableSharedFlow<NavDirections>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST,
    )
    val navigation = _navigation.shareIn(
        viewModelScope,
        SharingStarted.Lazily,
    )

    fun navigateTo(navDirections: NavDirections) {
        _navigation.tryEmit(navDirections)
    }

    fun navigateBack(): Boolean {
        viewModelScope.launch {
            _navigation.tryEmit(backDirection())
        }
        return true
    }

    open suspend fun backDirection(): NavDirections = BackNavDirections

    @CallSuper
    open fun onActive() {}
}

@HiltViewModel
class EmptyViewModel @Inject constructor(logger: Logger) : BaseViewModel(logger)