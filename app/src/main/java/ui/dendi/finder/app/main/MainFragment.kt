package ui.dendi.finder.app.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.app.R
import ui.dendi.finder.app.databinding.FragmentMainBinding
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.extension.collectWithLifecycle

@AndroidEntryPoint
class MainFragment : BaseFragment<MainViewModel>(R.layout.fragment_main) {

    override val viewModel: MainViewModel by viewModels()
    private val binding: FragmentMainBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBind()
    }

    private fun onBind() = with(binding) {
        collectWithLifecycle(viewModel.tabs) {
            tabBar.setTabs(it, viewModel.selectedTab)
        }

        tabBar.onTabSelected { tab ->
            (childFragmentManager.findFragmentById(R.id.navContainer) as NavHostFragment)
                .navController
                .setGraph(tab.graphId)
            viewModel.onTabSelected(tab)
        }
    }
}