package be.arnaud.rocketleaguestats.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.arnaud.rocketleaguestats.databinding.FragmentGlobalBinding
import be.arnaud.rocketleaguestats.ui.adapter.AnyPagerAdapter
import be.arnaud.rocketleaguestats.ui.global.ranking.GlobalRankingFragment
import be.arnaud.rocketleaguestats.ui.global.stats.GlobalStatsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class GlobalFragment : Fragment() {

    private var _binding: FragmentGlobalBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        println("view create")
        _binding = FragmentGlobalBinding.inflate(inflater, container, false)

        binding.globalStatsViewPager.adapter = AnyPagerAdapter(this, listOf(GlobalRankingFragment(), GlobalStatsFragment()))
        binding.globalStatsViewPager.offscreenPageLimit = 1

        TabLayoutMediator(binding.globalStatsTabLayout, binding.globalStatsViewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Ranking"
                else -> "Stats"
            }
        }.attach()

        binding.globalStatsTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.globalStatsViewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}