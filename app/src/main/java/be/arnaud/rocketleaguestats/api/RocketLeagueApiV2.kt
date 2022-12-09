package be.arnaud.rocketleaguestats.api

import be.arnaud.rocketleaguestats.api.models.Profile
import be.arnaud.rocketleaguestats.api.models.ProfileSegmentData
import be.arnaud.rocketleaguestats.api.models.Search
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RocketLeagueApiV2 {

    @GET("search")
    fun search(
        @Query("platform") platform: String,
        @Query("query") query: String,
        @Query("autocomplete") autoComplete: Boolean,
    ): Call<Search>

    @GET("profile/{platform}/{identifier}")
    fun getProfile(
        @Path("identifier") identifier: String,
        @Path("platform") platform: String
    ): Call<Profile>

    @GET("profile/{platform}/{identifier}/segments/playlist")
    fun getProfileSegment(
        @Path("identifier") identifier: String,
        @Path("platform") platform: String,
        @Query("season") season: Int
    ): Call<ProfileSegmentData>
}