package ui.dendi.android.finder.app.activity

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.android.finder.R
import ui.dendi.android.finder.core.base.BaseViewModel
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