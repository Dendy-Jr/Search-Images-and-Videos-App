package com.dendi.android.search_images_and_videos_app.core.base

import com.dendi.android.search_images_and_videos_app.app.navigation.AppNavDirections
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appNavDirections: AppNavDirections,
) : BaseViewModel() {

    init {
//        navigator.navigateTo(appNavDirections.mainScreen)
    }
}