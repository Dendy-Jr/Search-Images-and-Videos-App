package com.dendi.android.search_images_and_videos_app.core.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.app.activity.MainActivity
import com.dendi.android.search_images_and_videos_app.app.navigation.Navigator
import com.dendi.android.search_images_and_videos_app.app.navigation.setupWithNavController
import com.dendi.android.search_images_and_videos_app.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : BaseFragment<EmptyViewModel>(R.layout.fragment_main) {

    override fun setRecyclerView(): RecyclerView? = null
    override val viewModel: EmptyViewModel by viewModels()
    private val binding by viewBinding(FragmentMainBinding::bind)

    @Inject
    lateinit var navigator: Navigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        onBind()
    }

    private fun onBind() = with(binding) {
        val navController = fragmentContainer.getFragment<NavHostFragment>().navController
        navigator.setupWithNavController(requireActivity(), navController)
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)
        NavigationUI.setupActionBarWithNavController(MainActivity(), navController)
    }
}