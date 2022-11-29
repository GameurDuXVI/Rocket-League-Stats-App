package be.arnaud.rocketleaguestats.ui.global.listeners

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import be.arnaud.rocketleaguestats.api.Platform

class PlatformItemSelectedListener(private val platform: MutableLiveData<Platform>): AdapterView.OnItemSelectedListener {


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view?.tag is Platform) {
            val newValue = view.tag as Platform
            if (platform.value != newValue) {
                platform.value = newValue
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do
    }
}