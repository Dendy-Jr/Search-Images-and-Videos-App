package com.dendi.android.search_images_and_videos_app.app.navigation

import androidx.activity.ComponentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityRetainedScoped
class Navigator @Inject constructor() {

    private val _navEvents = Channel<Direction>(Channel.UNLIMITED)
    val navEvents: Flow<Direction> = _navEvents.receiveAsFlow()

    fun navigateTo(navDirections: NavDirections) {
        _navEvents.trySend(Direction.NavigateTo(navDirections))
    }

    fun navigateBack() {
        _navEvents.trySend(Direction.NavigateBack)
    }

    sealed interface Direction {
        data class NavigateTo(
            val navDirections: NavDirections,
        ) : Direction

        object NavigateBack : Direction
    }
}

fun Navigator.setupWithNavController(
    activity: ComponentActivity,
    navController: NavController,
) {
    activity.lifecycleScope.launch {
        activity.repeatOnLifecycle(Lifecycle.State.STARTED) {
            navEvents.collect { direction ->
                when (direction) {
                    is Navigator.Direction.NavigateTo -> {
                        navController.navigate(direction.navDirections)
                    }

                    is Navigator.Direction.NavigateBack -> {
                        if (navController.popBackStack().not()) {
                            activity.finish()
                        }
                    }
                }
            }
        }
    }
}