package ui.dendi.android.finder.core.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.android.finder.R
import ui.dendi.android.finder.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    private val binding: FragmentMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBind()
    }

    private fun onBind() = with(binding) {
        val navHost = childFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment
        val navController = navHost.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
    }
}