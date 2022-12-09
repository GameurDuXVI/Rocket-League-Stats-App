package be.arnaud.rocketleaguestats.ui.global.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.RestApi
import be.arnaud.rocketleaguestats.databinding.FragmentGlobalStatBinding
import be.arnaud.rocketleaguestats.ui.MainActivity
import be.arnaud.rocketleaguestats.ui.global.adapters.BoardSpinnerAdapter
import be.arnaud.rocketleaguestats.ui.global.adapters.PlatformSpinnerAdapter
import be.arnaud.rocketleaguestats.ui.global.adapters.StatsLeaderBoardAdapter
import be.arnaud.rocketleaguestats.ui.global.listeners.BoardItemSelectedListener
import be.arnaud.rocketleaguestats.ui.global.listeners.PlatformItemSelectedListener

class GlobalStatsFragment : Fragment(), AbsListView.OnScrollListener {

    private var _binding: FragmentGlobalStatBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GlobalStatsViewModel
    private lateinit var footer: View
    private var vlAdapter: StatsLeaderBoardAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[GlobalStatsViewModel::class.java]
        _binding = FragmentGlobalStatBinding.inflate(inflater, container, false)

        // Add footer
        footer = LayoutInflater.from(activity!!).inflate(R.layout.footer_loading, null)
        binding.globalStatsListview.addFooterView(footer)

        // Config spinner
        val platform = binding.globalStatsPlatformSpinner
        platform.adapter = PlatformSpinnerAdapter(activity!!)
        platform.onItemSelectedListener = PlatformItemSelectedListener(viewModel.platform)
        val board = binding.globalStatsBoardSpinner
        board.adapter = BoardSpinnerAdapter(activity!!)
        board.onItemSelectedListener = BoardItemSelectedListener(viewModel.board)

        // Define observer
        val fetch: () -> Unit = {
            RestApi.getStatsLeaderBoard(
                viewModel.board.value!!,
                viewModel.platform.value!!,
                0
            ) { leaderBoard ->
                if (leaderBoard != null) {
                    vlAdapter = StatsLeaderBoardAdapter(activity!!, leaderBoard.data.items, 0)
                    binding.globalStatsListview.adapter = vlAdapter
                }
            }
        }

        binding.globalStatsListview.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                if (vlAdapter == null) {
                    return@OnItemClickListener
                }
                val item = vlAdapter!!.getItem(position)!!
                val bundle = Bundle()
                bundle.putString("identifier", item.owner.metadata.platformUserIdentifier)
                bundle.putString("platform", item.owner.metadata.platformSlug)
                (activity as MainActivity).navigate(R.id.nav_individual, bundle)
            }

        // Observing view model for changes
        viewModel.platform.observe(viewLifecycleOwner) { fetch() }
        viewModel.board.observe(viewLifecycleOwner) { fetch() }

        binding.globalStatsListview.setOnScrollListener(this)

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
        view: AbsListView?,
        firstVisibleItem: Int,
        visibleItemCount: Int,
        totalItemCount: Int
    ) {
        if (vlAdapter == null) {
            return
        }
        if (view?.lastVisiblePosition == totalItemCount - 1 && totalItemCount > 10 && !viewModel.loading.value!!) {
            println("Scroll " + totalItemCount)
            viewModel.loading.value = true

            vlAdapter!!.page++

            RestApi.getStatsLeaderBoard(
                viewModel.board.value!!,
                viewModel.platform.value!!,
                vlAdapter!!.page
            ) { leaderBoard ->
                if (leaderBoard != null) {
                    vlAdapter!!.addAll(leaderBoard.data.items)
                }
                viewModel.loading.value = false
            }
        }
    }
}