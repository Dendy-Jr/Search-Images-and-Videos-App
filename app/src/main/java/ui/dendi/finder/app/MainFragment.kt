package ui.dendi.finder.app

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.app.databinding.FragmentMainBinding
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.base.EmptyViewModel

@AndroidEntryPoint
class MainFragment : BaseFragment<EmptyViewModel>(R.layout.fragment_main) {

    override val viewModel: EmptyViewModel by viewModels()
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