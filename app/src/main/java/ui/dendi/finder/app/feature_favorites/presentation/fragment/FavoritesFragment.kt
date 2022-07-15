package ui.dendi.finder.app.feature_favorites.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.app.R
import ui.dendi.finder.app.core.base.BaseFragment
import ui.dendi.finder.app.core.base.EmptyViewModel
import ui.dendi.finder.app.databinding.FragmentFavoritesBinding
import ui.dendi.finder.app.feature_favorites.presentation.adapter.FavoritesAdapter

@AndroidEntryPoint
class FavoritesFragment : BaseFragment<EmptyViewModel>(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)
    override val viewModel: EmptyViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBind()
    }

    private fun onBind() = with(binding) {
        val fragmentList = arrayListOf<Fragment>(
            FavoritesImageFragment(),
            FavoritesVideoFragment(),
        )

        val adapter = FavoritesAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle,
        )
        pager.adapter = adapter
        pager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, pager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = IMAGES
                }
                1 -> {
                    tab.text = VIDEOS
                }
            }
        }.attach()
    }

    private companion object {
        const val IMAGES = "Images"
        const val VIDEOS = "Videos"
    }
}