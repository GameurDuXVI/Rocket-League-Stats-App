package be.arnaud.rocketleaguestats.ui.individual.ranking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.RestApi
import be.arnaud.rocketleaguestats.databinding.FragmentIndividualRankingBinding
import be.arnaud.rocketleaguestats.ui.global.adapters.SeasonSpinnerAdapter
import be.arnaud.rocketleaguestats.ui.global.listeners.SeasonItemSelectedListener
import be.arnaud.rocketleaguestats.ui.individual.IndividualViewModel


class IndividualRankingFragment(val identifier: String, val platform: Platform) : Fragment() {

    private var _binding: FragmentIndividualRankingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: IndividualViewRankingModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[IndividualViewRankingModel::class.java]
        _binding = FragmentIndividualRankingBinding.inflate(inflater, container, false)

        // Config spinner
        val season = binding.individualRankingSeasonSpinner
        season.adapter = SeasonSpinnerAdapter(requireContext())
        season.onItemSelectedListener = SeasonItemSelectedListener(viewModel.season)

        // Define observer
        val fetch: () -> Unit = {
            // Get data from api
            RestApi.getProfileSegment(identifier, platform, viewModel.season.value!!) {
                if (it != null) {
                    // Apply data to the ui
                    binding.individualRankingRecycleView.adapter =
                        IndividualRankingRecycleViewAdapter(this, it.data)
                    if (binding.individualRankingRecycleView.layoutManager == null) {
                        binding.individualRankingRecycleView.layoutManager =
                            LinearLayoutManager(requireContext())
                    }
                }
            }
        }

        // Observing view model for changes
        viewModel.season.observe(viewLifecycleOwner){ fetch() }

        fetch()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.title = ""
    }
}