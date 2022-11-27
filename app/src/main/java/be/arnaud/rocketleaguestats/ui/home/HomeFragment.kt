package be.arnaud.rocketleaguestats.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.RestApi
import be.arnaud.rocketleaguestats.databinding.FragmentHomeBinding

class HomeFragment : Fragment(), AbsListView.OnScrollListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var footer: View
    private var leaderboardAdapter: LeaderBoardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        footer = LayoutInflater.from(activity!!).inflate(R.layout.loading_footer, null)
        binding.homeListview.addFooterView(footer)

        // Config spinner
        binding.homePlatformSpinner.adapter = PlatformSpinnerAdapter(activity!!)
        binding.homePlatformSpinner.onItemSelectedListener = HomePlatformItemSelectedListener(homeViewModel)
        binding.homeBoardSpinner.adapter = BoardSpinnerAdapter(activity!!)
        binding.homeBoardSpinner.onItemSelectedListener = HomeBoardItemSelectedListener(homeViewModel)

        // Define observer
        val observer: () -> Unit = {
            RestApi.getStatsLeaderBoard(homeViewModel.board.value!!, homeViewModel.platform.value!!, 0) { leaderBoard ->
                if (leaderBoard != null) {
                    leaderboardAdapter = LeaderBoardAdapter(activity!!, leaderBoard.data.items, 0)
                    binding.homeListview.adapter = leaderboardAdapter
                }
            }
        }

        // Observing view model for changes
        homeViewModel.platform.observe(viewLifecycleOwner){
            observer()
        }
        homeViewModel.board.observe(viewLifecycleOwner){
            observer()
        }

        binding.homeListview.setOnScrollListener(this)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        // Nothing to do
    }

    override fun onScroll(view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int) {
        if (leaderboardAdapter == null){
            return
        }
        if (view?.lastVisiblePosition == totalItemCount - 1 && totalItemCount > 10 && !homeViewModel.loading.value!!){
            homeViewModel.loading.value = true

            leaderboardAdapter!!.page++

            RestApi.getStatsLeaderBoard(homeViewModel.board.value!!, homeViewModel.platform.value!!, leaderboardAdapter!!.page){
                    leaderBoard ->
                if (leaderBoard != null) {
                    leaderboardAdapter!!.addAll(leaderBoard.data.items)
                }
                homeViewModel.loading.value = false
            }
        }
    }
}