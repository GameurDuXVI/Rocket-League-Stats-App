package be.arnaud.rocketleaguestats.api

import be.arnaud.rocketleaguestats.api.models.LeaderBoard
import be.arnaud.rocketleaguestats.api.models.Profile
import be.arnaud.rocketleaguestats.api.models.ProfileSegmentData
import be.arnaud.rocketleaguestats.api.models.Search
import be.arnaud.rocketleaguestats.api.models.search.SearchData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

/**
 * @source https://medium.com/android-news/kotlin-coroutines-and-retrofit-e0702d0b8e8f
 */
object RestApi {
    private var rocketLeagueApiV1: RocketLeagueApiV1? = null
    private var rocketLeagueApiV2: RocketLeagueApiV2? = null

    private fun getRocketLeagueApiV1(): RocketLeagueApiV1 {
        if (rocketLeagueApiV1 == null) {
            rocketLeagueApiV1 = Retrofit.Builder().baseUrl("https://api.tracker.gg/api/v1/rocket-league/standard/")
                .addConverterFactory(MoshiConverterFactory.create()).build()
                .create(RocketLeagueApiV1::class.java)
        }
        return rocketLeagueApiV1!!
    }

    private fun getRocketLeagueApiV2(): RocketLeagueApiV2 {
        if (rocketLeagueApiV2 == null) {
            rocketLeagueApiV2 = Retrofit.Builder().baseUrl("https://api.tracker.gg/api/v2/rocket-league/standard/")
                .addConverterFactory(MoshiConverterFactory.create()).build()
                .create(RocketLeagueApiV2::class.java)
        }
        return rocketLeagueApiV2!!
    }

    private fun <T> call(
        call: Call<T>, callback: (obj: T?) -> Unit
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // TODO Remove the log message
                println(call.request().url().url().toString())
                val response = call.execute()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        callback(response.body())
                    } else {
                        callback(null)
                        error(response.message())
                    }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
    }

    fun getLeaderBoard(
        type: LeaderBoard.Type,
        board: LeaderBoard.Board,
        platform: Platform,
        playlist: PlayList,
        season: Season,
        page: Int,
        callback: (leaderBoard: LeaderBoard?) -> Unit
    ) {
        val maxPerPage = 100
        val toSkip = if (page - 1 >= 0) page - 1 else 0
        val finalPlaylist = when(playlist) {
            PlayList.NONE -> null
            PlayList.UN_RANKED -> if (season.id <= 14) PlayList.UN_RANKED_OLD.id else playlist.id
            else -> playlist.id
        }
        val call = getRocketLeagueApiV1().getLeaderBoard(
            type.typeName,
            platform.typeName,
            if (board == LeaderBoard.Board.NONE) null else board.typeName,
            finalPlaylist,
            toSkip * maxPerPage,
            maxPerPage,
            if (season == Season.NONE) null else season.id
        )
        call(call, callback)
    }

    fun getStatsLeaderBoard(
        board: LeaderBoard.Board,
        platform: Platform,
        page: Int,
        callback: (leaderBoard: LeaderBoard?) -> Unit
    ) {
        getLeaderBoard(
            LeaderBoard.Type.STATS, board, platform, PlayList.NONE, Season.NONE, page, callback
        )
    }

    fun getRankingLeaderBoard(
        platform: Platform,
        season: Season,
        playlist: PlayList,
        page: Int,
        callback: (leaderBoard: LeaderBoard?) -> Unit
    ) {
        getLeaderBoard(
            LeaderBoard.Type.PLAYLIST,
            LeaderBoard.Board.NONE,
            platform,
            playlist,
            season,
            page,
            callback
        )
    }

    private fun search(
        platform: Platform,
        query: String,
        callback: (search: Search?) -> Unit
    ) {
        val call = getRocketLeagueApiV2().search(platform.typeName, query, true)
        call(call, callback)
    }

    fun search(
        query: String,
        callback: (data: List<SearchData>) -> Unit
    ) {
        if (query.isEmpty()) {
            callback(emptyList())
            return
        }

        var completed = 0
        val data = ArrayList<SearchData>()
        val platforms = Platform.values().filter { platform -> platform != Platform.ALL }
        for (platform in platforms) {
            search(platform, query) { search ->
                completed++

                if (search != null) {
                    data.addAll(search.data)
                }

                if (completed == platforms.size) {
                    callback(data)
                }
            }
        }
    }

    fun getProfile(
        name: String,
        platform: Platform,
        callback: (profile: Profile?) -> Unit
    ) {
        val call = getRocketLeagueApiV2().getProfile(
            name,
            platform.typeName
        )
        call(call, callback)
    }

    fun getProfileSegment(
        identifier: String,
        platform: Platform,
        season: Season,
        callback: (profile: ProfileSegmentData?) -> Unit
    ) {
        val call = getRocketLeagueApiV2().getProfileSegment(
            identifier,
            platform.typeName,
            season.id
        )
        call(call, callback)
    }
}