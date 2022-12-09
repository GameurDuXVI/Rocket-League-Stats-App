package be.arnaud.rocketleaguestats.api

import be.arnaud.rocketleaguestats.api.models.LeaderBoard
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RocketLeagueApiV1 {
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