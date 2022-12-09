package be.arnaud.rocketleaguestats.ui.individual

import androidx.lifecycle.ViewModel
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.Season
import be.arnaud.rocketleaguestats.utils.ExtendedLiveData

class IndividualViewModel : ViewModel() {
    var identifier = ""
    var platform = Platform.ALL
    val season = ExtendedLiveData(Season.values().last())
}