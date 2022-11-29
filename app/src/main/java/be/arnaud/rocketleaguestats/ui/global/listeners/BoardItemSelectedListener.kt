package be.arnaud.rocketleaguestats.ui.global.listeners

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import be.arnaud.rocketleaguestats.api.LeaderBoard

class BoardItemSelectedListener(private val board: MutableLiveData<LeaderBoard.Board>): AdapterView.OnItemSelectedListener {


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view?.tag is LeaderBoard.Board) {
            val newValue = view.tag as LeaderBoard.Board
            if (board.value != newValue) {
                board.value = newValue
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do
    }
}