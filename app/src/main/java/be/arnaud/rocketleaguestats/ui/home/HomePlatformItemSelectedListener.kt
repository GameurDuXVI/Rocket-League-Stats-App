package be.arnaud.rocketleaguestats.ui.home

import android.view.View
import android.widget.AdapterView
import be.arnaud.rocketleaguestats.api.Platform

class HomePlatformItemSelectedListener(private val homeViewModel: HomeViewModel): AdapterView.OnItemSelectedListener {


    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (view?.tag is Platform) {
            val platform = view?.tag as Platform
            homeViewModel.platform.value = platform
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        homeViewModel.platform.value = Platform.ALL
    }
}