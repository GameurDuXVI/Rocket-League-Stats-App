package be.arnaud.rocketleaguestats.ui.global.ranking

import androidx.lifecycle.ViewModel
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.api.PlayList
import be.arnaud.rocketleaguestats.api.Season
import be.arnaud.rocketleaguestats.utils.ExtendedLiveData

class GlobalRankingViewModel : ViewModel() {
    val platform = ExtendedLiveData(Platform.ALL)
    val season = ExtendedLiveData(Season.values().last())
    val playlist = ExtendedLiveData(PlayList.RANKED1v1)
    val loading = ExtendedLiveData(false)
}