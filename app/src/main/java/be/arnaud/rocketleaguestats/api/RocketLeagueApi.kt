package be.arnaud.rocketleaguestats.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RocketLeagueApi {
    @GET("leaderboards")
    fun getLeaderBoard(
        @Query("type") type: String,
        @Query("platform") platform: String,
        @Query("board") board: String?,
        @Query("playlist") playlist: Int?,
        @Query("skip") skip: Int,
        @Query("take") take: Int,
        @Query("season") season: Int?
    ): Call<LeaderBoard>
}