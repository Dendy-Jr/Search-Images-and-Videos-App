package com.dendi.android.search_images_and_videos_app.app.activity

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.core.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {

    var navController: NavController? = null

    override fun onActive() {
        super.onActive()

        viewModelScope.launch {
            navController?.navigate(R.id.mainFragment)
        }
    }
}