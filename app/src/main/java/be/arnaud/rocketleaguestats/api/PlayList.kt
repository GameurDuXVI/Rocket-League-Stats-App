package be.arnaud.rocketleaguestats.api

import be.arnaud.rocketleaguestats.R

enum class PlayList(val id: Int, val resourceId: Int) {
    NONE(-1, -1),
    RANKED1v1(10, R.string.ranking_ranked1v1),
    RANKED2v2(11, R.string.ranking_ranked2v2),
    RANKED3v3(13, R.string.ranking_ranked3v3),
    UN_RANKED(0, R.string.ranking_unranked),
    HOOPS(27, R.string.ranking_hoops),
    RUMBLE(28, R.string.ranking_rumble),
    DROP_SHOT(29, R.string.ranking_drop_shot),
    SNOW_DAY(30, R.string.ranking_snow_day),
    TOURNAMENT_MATCHES(34, R.string.ranking_tournament_matches);


    companion object {
        fun fromId(id: Int?): PlayList? {
            for (playlist in values()) {
                if (playlist.id == id) {
                    return playlist
                }
            }
            return null
        }
    }
}