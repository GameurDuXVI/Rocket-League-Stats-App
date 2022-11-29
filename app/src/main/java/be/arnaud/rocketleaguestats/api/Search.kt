package be.arnaud.rocketleaguestats.api

import be.arnaud.rocketleaguestats.api.search.SearchData

data class Search(
    val data: List<SearchData>
) {

}