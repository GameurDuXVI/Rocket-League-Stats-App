package be.arnaud.rocketleaguestats.ui.global

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import be.arnaud.rocketleaguestats.databinding.FragmentGlobalBinding
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
        _binding = FragmentGlobalBinding.inflate(inflater, container, false)

        binding.globalStatsViewPager.adapter = GlobalPagerAdapter(this, 2)

        TabLayoutMediator(binding.globalStatsTabLayout, binding.globalStatsViewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Stats"
                else -> "Ranking"
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