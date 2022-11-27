package be.arnaud.rocketleaguestats.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import be.arnaud.rocketleaguestats.api.LeaderBoard
import be.arnaud.rocketleaguestats.api.Platform

class HomeViewModel : ViewModel() {

    private val _platformLiveData: MutableLiveData<Platform> = MutableLiveData<Platform>().apply {
        value = Platform.ALL
    }
    val platform: MutableLiveData<Platform> = _platformLiveData

    private val _board: MutableLiveData<LeaderBoard.Board> = MutableLiveData<LeaderBoard.Board>().apply {
        value = LeaderBoard.Board.GOALS
    }
    val board: MutableLiveData<LeaderBoard.Board> = _board

    private val _loading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
    val loading: MutableLiveData<Boolean> = _loading
}