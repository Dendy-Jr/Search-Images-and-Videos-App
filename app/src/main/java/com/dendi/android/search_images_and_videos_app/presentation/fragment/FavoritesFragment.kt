package com.dendi.android.search_images_and_videos_app.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.dendi.android.search_images_and_videos_app.R
import com.dendi.android.search_images_and_videos_app.databinding.FragmentFavoritesBinding
import com.dendi.android.search_images_and_videos_app.presentation.adapter.FavoritesAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : BaseFragment(R.layout.fragment_favorites) {

    private val binding by viewBinding(FragmentFavoritesBinding::bind)

    override fun setRecyclerView(): RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf<Fragment>(
            FavoritesImageFragment(),
            FavoritesVideoFragment()
        )

        val adapter = FavoritesAdapter(
            fragmentList,
            childFragmentManager,
            lifecycle
        )
        binding.pager.adapter = adapter
        binding.pager.isUserInputEnabled = false

        TabLayoutMediator(
            binding.tabLayout, binding.pager
        ) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Images"
                }
                1 -> {
                    tab.text = "Videos"
                }
            }
        }.attach()
    }
}