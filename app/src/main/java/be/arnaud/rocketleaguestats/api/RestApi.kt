package be.arnaud.rocketleaguestats.api

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
    private const val BASE_URL = "https://api.tracker.gg/api/v1/rocket-league/standard/"
    private var rocketLeagueApi: RocketLeagueApi? = null

    private fun getRocketLeagueApi(): RocketLeagueApi {
        if (rocketLeagueApi == null) {
            rocketLeagueApi = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(MoshiConverterFactory.create())
                .build().create(RocketLeagueApi::class.java);
        }
        return rocketLeagueApi!!
    }

    private fun <T> call(
        call: Call<T>,
        callback: (obj: T?) -> Unit
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
        page: Int,
        callback: (leaderBoard: LeaderBoard?) -> Unit
    ) {
        val maxPerPage = 100
        val toSkip = if (page - 1 >= 0) page - 1 else 0
        val service = getRocketLeagueApi()
        val call = service.getLeaderBoard(type.typeName, platform.typeName, board.typeName, if (playlist == PlayList.NONE) null else playlist.id, toSkip * maxPerPage, maxPerPage)
        call(call, callback)
    }

    fun getStatsLeaderBoard(
        board: LeaderBoard.Board,
        platform: Platform,
        page: Int,
        callback: (leaderBoard: LeaderBoard?) -> Unit
    ) {
        getLeaderBoard(LeaderBoard.Type.STATS, board, platform, PlayList.NONE, page, callback)
    }
}