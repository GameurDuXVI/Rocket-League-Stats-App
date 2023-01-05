package be.arnaud.rocketleaguestats.ui.global.listeners

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import be.arnaud.rocketleaguestats.api.Platform

class PlatformItemSelectedListener(private val platform: MutableLiveData<Platform>): AdapterView.OnItemSelectedListener {


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        // Check if the tag is a platform
        if (view?.tag is Platform) {
            val newValue = view.tag as Platform
            // Change value if it is new
            if (platform.value != newValue) {
                platform.value = newValue
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Nothing to do
    }
}