package com.dendi.android.search_images_and_videos_app.core.base

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

interface ViewModelOwner<VM : BaseViewModel> {
    val viewModel: VM
}

open class BaseViewModel : ViewModel() {

}

@HiltViewModel
class EmptyViewModel @Inject constructor() : BaseViewModel()