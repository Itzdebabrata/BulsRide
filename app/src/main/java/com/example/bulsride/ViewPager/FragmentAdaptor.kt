package com.example.bulsride.ViewPager

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.fragment.app.Fragment as Fragment

class FragmentAdaptor(Fragemt:FragmentManager): FragmentStatePagerAdapter(Fragemt,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getItem(position: Int): Fragment {
        when(position){
            0-> return FragmentOne()
            1-> return FragmentTwo()
        }
        throw IllegalStateException("position $position is invalid for this viewpager")
    }

    override fun getCount(): Int {
        return 2
    }
}