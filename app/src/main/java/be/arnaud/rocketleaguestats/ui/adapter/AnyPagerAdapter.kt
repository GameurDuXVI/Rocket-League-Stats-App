package be.arnaud.rocketleaguestats.ui.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AnyPagerAdapter(fm: Fragment, private val pages: List<Fragment>) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return pages.size
    }

    override fun createFragment(position: Int): Fragment {
        return pages[position]
    }


}