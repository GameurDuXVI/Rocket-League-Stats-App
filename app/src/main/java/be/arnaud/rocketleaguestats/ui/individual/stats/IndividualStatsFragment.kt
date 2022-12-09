package be.arnaud.rocketleaguestats.ui.individual.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import be.arnaud.rocketleaguestats.databinding.FragmentIndividualStatsBinding
import be.arnaud.rocketleaguestats.ui.global.adapters.SeasonSpinnerAdapter
import be.arnaud.rocketleaguestats.ui.global.listeners.SeasonItemSelectedListener
import be.arnaud.rocketleaguestats.ui.individual.IndividualViewModel


class IndividualStatsFragment : Fragment() {

    private var _binding: FragmentIndividualStatsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: IndividualViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[IndividualViewModel::class.java]
        _binding = FragmentIndividualStatsBinding.inflate(inflater, container, false)

        val season = binding.individualStatsSeasonSpinner
        season.adapter = SeasonSpinnerAdapter(activity!!)
        season.onItemSelectedListener = SeasonItemSelectedListener(viewModel.season)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.title = ""
    }
}