package com.example.hackernewsapp.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackernewsapp.ui.fragments.BestStoriesFragment
import com.example.hackernewsapp.ui.fragments.NewStoriesFragment
import com.example.hackernewsapp.ui.fragments.TopStoriesFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> NewStoriesFragment()
            1 -> TopStoriesFragment()
            2 -> BestStoriesFragment()
            else -> NewStoriesFragment()
        }
    }
}