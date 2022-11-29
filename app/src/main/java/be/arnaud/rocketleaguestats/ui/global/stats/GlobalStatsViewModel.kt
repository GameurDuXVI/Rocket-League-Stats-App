package be.arnaud.rocketleaguestats.ui.global.stats

import androidx.lifecycle.ViewModel
import be.arnaud.rocketleaguestats.api.LeaderBoard
import be.arnaud.rocketleaguestats.api.Platform
import be.arnaud.rocketleaguestats.utils.ExtendedLiveData

class GlobalStatsViewModel : ViewModel() {
    val platform = ExtendedLiveData(Platform.ALL)
    val board = ExtendedLiveData(LeaderBoard.Board.GOALS)
    val loading = ExtendedLiveData(false)
}