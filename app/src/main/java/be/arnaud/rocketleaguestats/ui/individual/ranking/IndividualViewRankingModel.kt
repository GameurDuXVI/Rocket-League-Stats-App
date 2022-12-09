package be.arnaud.rocketleaguestats.ui.individual.ranking

import androidx.lifecycle.ViewModel
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.Season
import be.arnaud.rocketleaguestats.utils.ExtendedLiveData

class IndividualViewRankingModel : ViewModel() {
    val season = ExtendedLiveData(Season.values().last())
}