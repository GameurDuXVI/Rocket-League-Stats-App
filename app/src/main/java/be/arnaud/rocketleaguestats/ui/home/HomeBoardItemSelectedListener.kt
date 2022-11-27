package be.arnaud.rocketleaguestats.ui.home

import android.view.View
import android.widget.AdapterView
import be.arnaud.rocketleaguestats.api.LeaderBoard
import be.arnaud.rocketleaguestats.api.Platform

class HomeBoardItemSelectedListener(private val homeViewModel: HomeViewModel): AdapterView.OnItemSelectedListener {


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view?.tag is LeaderBoard.Board) {
            val board = view?.tag as LeaderBoard.Board
            homeViewModel.board.value = board
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        homeViewModel.board.value = LeaderBoard.Board.GOALS
    }
}