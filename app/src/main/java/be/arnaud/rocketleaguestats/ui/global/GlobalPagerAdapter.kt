package be.arnaud.rocketleaguestats.ui.global

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import be.arnaud.rocketleaguestats.ui.global.ranking.GlobalRankingFragment
import be.arnaud.rocketleaguestats.ui.global.stats.GlobalStatsFragment

class GlobalPagerAdapter(fm: Fragment, private var totalTabs: Int) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return totalTabs
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> GlobalStatsFragment()
            else -> GlobalRankingFragment()
        }
    }


}