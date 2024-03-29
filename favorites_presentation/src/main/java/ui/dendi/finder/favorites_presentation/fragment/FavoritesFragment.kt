package ui.dendi.finder.favorites_presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import ui.dendi.finder.core.core.base.BaseFragment
import ui.dendi.finder.core.core.base.EmptyViewModel
import ui.dendi.finder.favorites_presentation.R
import ui.dendi.finder.favorites_presentation.adapter.FavoritesAdapter
import ui.dendi.finder.favorites_presentation.databinding.FragmentFavoritesBinding

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
            parentFragmentManager,
            lifecycle,
        )
        pager.adapter = adapter
        pager.isUserInputEnabled = false

        TabLayoutMediator(tabLayout, pager, true, false) { tab, position ->
            when (position) {
                IMAGES_POSITION -> {
                    tab.text = IMAGES
                }
                VIDEOS_POSITION -> {
                    tab.text = VIDEOS
                }
            }
        }.attach()
    }

    private companion object {
        const val IMAGES = "Images"
        const val VIDEOS = "Videos"

        const val IMAGES_POSITION = 0
        const val VIDEOS_POSITION = 1
    }
}