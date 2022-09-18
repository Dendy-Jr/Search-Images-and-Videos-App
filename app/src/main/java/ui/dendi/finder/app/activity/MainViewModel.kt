package ui.dendi.finder.app.activity

import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ui.dendi.finder.app.R
import ui.dendi.finder.core.core.base.BaseViewModel
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