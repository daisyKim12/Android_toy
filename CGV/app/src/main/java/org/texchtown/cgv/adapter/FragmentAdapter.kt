package org.texchtown.cgv.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.texchtown.cgv.fragment.*

class FragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle)
    : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        var tempFragment: Fragment = HomeFragment()
        when(position) {
            0 -> tempFragment = HomeFragment()
            1 -> tempFragment = EventFragment()
            2 -> tempFragment = MovieTalkFragment()
            3 -> tempFragment = FastOrderFragment()
            4 -> tempFragment = GiftShopFragment()
            5 -> tempFragment = AtCgvFragment()
        }
        return tempFragment
    }
}