package com.example.hackernewsapp.ui.adapter

import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.hackernewsapp.ui.fragments.NewStoriesFragment

/**
 * @author Edoardo Di Chiara
 */
class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int) = NewStoriesFragment.newInstance(position)
}