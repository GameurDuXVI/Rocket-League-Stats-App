package be.arnaud.rocketleaguestats.ui.individual

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.RestApi
import be.arnaud.rocketleaguestats.databinding.FragmentIndividualBinding
import be.arnaud.rocketleaguestats.db.DbUtils
import be.arnaud.rocketleaguestats.db.models.SearchHistory
import be.arnaud.rocketleaguestats.ui.MainActivity
import be.arnaud.rocketleaguestats.ui.adapter.AnyPagerAdapter
import be.arnaud.rocketleaguestats.ui.individual.ranking.IndividualRankingFragment
import be.arnaud.rocketleaguestats.ui.individual.stats.IndividualStatsFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.URL
import java.util.*


class IndividualFragment : Fragment() {

    private var _binding: FragmentIndividualBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: IndividualViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[IndividualViewModel::class.java]
        _binding = FragmentIndividualBinding.inflate(inflater, container, false)

        arguments?.let { bundle ->
            viewModel.identifier = bundle?.getString("identifier")!!
            viewModel.platform =
                Platform.values()
                    .first { platform -> platform.typeName == bundle.getString("platform")!! }
        }


        (activity as MainActivity).toolbar.title = ""

        RestApi.getProfile(
            viewModel.identifier,
            viewModel.platform
        ) { profile ->
            if (profile == null) {
                (activity as MainActivity).navigate(R.id.nav_home)
                return@getProfile
            }

            DbUtils.upsertSearchHistory(context!!, SearchHistory(viewModel.identifier, profile.data.platformInfo.platformUserHandle, profile.data.platformInfo.platformSlug, Date()))

            binding.individualName.text = profile.data.platformInfo.platformUserHandle
            CoroutineScope(Dispatchers.IO).launch {
                var url = profile.data.platformInfo.avatarUrl
                if (url == null) {
                    url = "https://imgsvc.trackercdn.com/url/size(128),fit(cover)/https%3A%2F%2Ftrackercdn.com%2Fcdn%2Frocketleague.tracker.network%2Fimages%2FdefaultAvatar.jpg/image.jpg"
                }
                var bitmap: Bitmap? = null
                try {
                    val inputStream: InputStream = URL(url).openStream()
                    bitmap = BitmapFactory.decodeStream(inputStream)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                withContext(Dispatchers.Main) {
                    if (bitmap != null) {
                        binding.individualIcon.setImageBitmap(bitmap)
                        binding.individualHeaderContainer.removeView(binding.individualLoad)
                    }
                }
            }
        }

        binding.individualViewPager.adapter = AnyPagerAdapter(this, listOf(IndividualRankingFragment(viewModel.identifier, viewModel.platform), IndividualStatsFragment(viewModel.identifier, viewModel.platform)))
        binding.individualViewPager.offscreenPageLimit = 1

        TabLayoutMediator(binding.individualTabLayout, binding.individualViewPager) { tab, position ->
            tab.text = when(position){
                0 -> "Ranking"
                else -> "Stats"
            }
        }.attach()

        binding.individualTabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                binding.individualViewPager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.title = ""
    }
}