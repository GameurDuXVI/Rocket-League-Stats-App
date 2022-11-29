package be.arnaud.rocketleaguestats.ui.global.listeners

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import be.arnaud.rocketleaguestats.api.PlayList

class PlayListItemSelectedListener(private val season: MutableLiveData<PlayList>): AdapterView.OnItemSelectedListener {


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view?.tag is PlayList) {
            val newValue = view.tag as PlayList
            if (season.value != newValue) {
                season.value = newValue
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do
    }
}