package com.dendi.android.search_images_and_videos_app.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class FavoritesAdapter(
    list: ArrayList<Fragment>,
    fm: FragmentManager,
    lifecycle: Lifecycle,
) : FragmentStateAdapter(fm, lifecycle) {

    private val fragments = list

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}