package be.arnaud.rocketleaguestats.ui.global.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.RestApi
import be.arnaud.rocketleaguestats.databinding.FragmentGlobalRankingBinding
import be.arnaud.rocketleaguestats.ui.global.adapters.PlatformSpinnerAdapter
import be.arnaud.rocketleaguestats.ui.global.adapters.PlayListSpinnerAdapter
import be.arnaud.rocketleaguestats.ui.global.adapters.RankingLeaderBoardAdapter
import be.arnaud.rocketleaguestats.ui.global.adapters.SeasonSpinnerAdapter
import be.arnaud.rocketleaguestats.ui.global.listeners.PlatformItemSelectedListener
import be.arnaud.rocketleaguestats.ui.global.listeners.PlayListItemSelectedListener
import be.arnaud.rocketleaguestats.ui.global.listeners.SeasonItemSelectedListener

class GlobalRankingFragment : Fragment(), AbsListView.OnScrollListener {

    private var _binding: FragmentGlobalRankingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GlobalRankingViewModel
    private lateinit var loadingFooter: View
    private lateinit var noDataFooter: View
    private var vlAdapter: RankingLeaderBoardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this)[GlobalRankingViewModel::class.java]
        _binding = FragmentGlobalRankingBinding.inflate(inflater, container, false)

        // Add footer
        loadingFooter = LayoutInflater.from(activity!!).inflate(R.layout.footer_loading, null)
        noDataFooter = LayoutInflater.from(activity!!).inflate(R.layout.footer_no_data, null)
        binding.globalRankingListview.addFooterView(loadingFooter)


        // Config spinner
        val platform = binding.globalRankingPlatformSpinner
        platform.adapter = PlatformSpinnerAdapter(activity!!)
        platform.onItemSelectedListener = PlatformItemSelectedListener(viewModel.platform)

        val season = binding.globalRankingSeasonSpinner
        season.adapter = SeasonSpinnerAdapter(activity!!)
        season.onItemSelectedListener = SeasonItemSelectedListener(viewModel.season)

        val playlist = binding.globalRankingPlaylistSpinner
        playlist.adapter = PlayListSpinnerAdapter(activity!!)
        playlist.onItemSelectedListener = PlayListItemSelectedListener(viewModel.playlist)

        // Define observer
        val fetch: () -> Unit = {
            RestApi.getRankingLeaderBoard(
                viewModel.platform.value!!, viewModel.season.value!!, viewModel.playlist.value!!, 0
            ) { leaderBoard ->
                if (leaderBoard == null) {
                    return@getRankingLeaderBoard
                }

                if (leaderBoard.data.items.isEmpty()) {
                    setNoDataFooter()
                } else {
                    setLoadingFooter()
                }

                vlAdapter = RankingLeaderBoardAdapter(activity!!, leaderBoard.data.items, 0)
                binding.globalRankingListview.adapter = vlAdapter
            }
        }

        // Observing view model for changes
        viewModel.platform.observe(viewLifecycleOwner) { fetch() }
        viewModel.season.observe(viewLifecycleOwner) { fetch() }
        viewModel.playlist.observe(viewLifecycleOwner) { fetch() }

        binding.globalRankingListview.setOnScrollListener(this)

        fetch()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun onScrollStateChanged(view: AbsListView?, scrollState: Int) {
        // Nothing to do
    }

    override fun onScroll(
        view: AbsListView?, firstVisibleItem: Int, visibleItemCount: Int, totalItemCount: Int
    ) {
        if (vlAdapter == null) {
            return
        }
        if (view?.lastVisiblePosition == totalItemCount - 1 && totalItemCount > 10 && !viewModel.loading.value!!) {
            viewModel.loading.value = true

            vlAdapter!!.page++

            RestApi.getRankingLeaderBoard(
                viewModel.platform.value!!,
                viewModel.season.value!!,
                viewModel.playlist.value!!,
                vlAdapter!!.page
            ) { leaderBoard ->
                if (leaderBoard != null) {
                    vlAdapter!!.addAll(leaderBoard.data.items)
                }
                viewModel.loading.value = false
            }
        }
    }

    private fun setLoadingFooter(){
        binding.globalRankingListview.removeFooterView(noDataFooter)
        if (binding.globalRankingListview.footerViewsCount == 0){
            binding.globalRankingListview.addFooterView(loadingFooter)
        }
    }

    private fun setNoDataFooter(){
        binding.globalRankingListview.removeFooterView(loadingFooter)
        if (binding.globalRankingListview.footerViewsCount == 0){
            binding.globalRankingListview.addFooterView(noDataFooter)
        }
    }
}