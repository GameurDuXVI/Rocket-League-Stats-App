package be.arnaud.rocketleaguestats.ui.individual.stats

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import be.arnaud.rocketleaguestats.R
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.RestApi
import be.arnaud.rocketleaguestats.databinding.FragmentIndividualStatsBinding


class IndividualStatsFragment(private val identifier: String, val platform: Platform) : Fragment() {

    private var _binding: FragmentIndividualStatsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentIndividualStatsBinding.inflate(inflater, container, false)

        RestApi.getProfile(identifier, platform) {
            if (it != null) {
                val stats =
                    it.data.segments.first { profileSegment -> profileSegment.type == "overview" }.stats
                binding.individualStatsRecycleView.adapter =
                    IndividualStatsRecycleViewAdapter(this,
                        listOf(IndividualStatsRecycleViewAdapter.Item(stats.goals, R.string.stats_goals),
                            IndividualStatsRecycleViewAdapter.Item(stats.assists, R.string.stats_assists),
                            IndividualStatsRecycleViewAdapter.Item(stats.mVPs, R.string.stats_mvp),
                            IndividualStatsRecycleViewAdapter.Item(stats.saves, R.string.stats_saves),
                            IndividualStatsRecycleViewAdapter.Item(stats.score, R.string.stats_trn_score),
                            IndividualStatsRecycleViewAdapter.Item(stats.shots, R.string.stats_shots),
                            IndividualStatsRecycleViewAdapter.Item(stats.goalShotRatio, R.string.stats_goal_shot_ratio),
                            IndividualStatsRecycleViewAdapter.Item(stats.tRNRating, R.string.stats_trn_rating),
                            IndividualStatsRecycleViewAdapter.Item(stats.wins, R.string.stats_wins),
                            IndividualStatsRecycleViewAdapter.Item(stats.seasonRewardLevel, R.string.stats_season_reward_level),
                            IndividualStatsRecycleViewAdapter.Item(stats.seasonRewardWins, R.string.stats_season_reward_wins))
                    )
                if (binding.individualStatsRecycleView.layoutManager == null) {
                    binding.individualStatsRecycleView.layoutManager =
                        LinearLayoutManager(requireContext())
                }
            }
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.actionBar?.title = ""
    }
}