package be.arnaud.rocketleaguestats.ui.global.listeners

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import be.arnaud.rocketleaguestats.api.Season

class SeasonItemSelectedListener(private val season: MutableLiveData<Season>): AdapterView.OnItemSelectedListener {


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view?.tag is Season) {
            val newValue = view.tag as Season
            if (season.value != newValue) {
                season.value = newValue
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do
    }
}