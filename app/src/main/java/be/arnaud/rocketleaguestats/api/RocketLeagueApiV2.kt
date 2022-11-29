package be.arnaud.rocketleaguestats.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RocketLeagueApiV2 {

    @GET("search")
    fun search(
        @Query("platform") platform: String,
        @Query("query") query: String,
        @Query("autocomplete") autoComplete: Boolean,
    ): Call<Search>
}