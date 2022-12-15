package be.arnaud.rocketleaguestats.api.models

import be.arnaud.rocketleaguestats.api.models.search.SearchData

data class Search(
    val data: List<SearchData>
) {

}