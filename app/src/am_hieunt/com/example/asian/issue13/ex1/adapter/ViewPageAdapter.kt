package com.example.asian.issue13.ex1.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.asian.issue13.ex2.fragment.AllFragment
import com.example.asian.issue13.ex2.fragment.FavoriteFragment

class ViewPageAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllFragment()
            1 -> FavoriteFragment()
            else -> AllFragment()
        }
    }
}