package site.yoonsang.tierwhere.src.main.search.profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import site.yoonsang.tierwhere.src.main.search.profile.model.MatchList
import site.yoonsang.tierwhere.src.main.search.profile.model.SummonerLeague

interface ProfileRetrofitInterface {

    @GET("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun getUserProfile(
        @Path("encryptedSummonerId") encryptedSummonerId: String
    ): Call<SummonerLeague>

    @GET("/lol/match/v4/matchlists/by-account/{encryptedAccountId}")
    fun getMatchesInfo(
        @Path("encryptedSummonerId") encryptedSummonerId: String,
        @Query("champion") champion: Set<Int>? = null,
        @Query("queue") queue: Set<Int>? = null,
        @Query("season") season: Set<Int>? = null,
        @Query("endTime") endTime: Long? = null,
        @Query("beginTime") beginTime: Long? = null,
        @Query("endIndex") endIndex: Int? = null,
        @Query("beginIndex") beginIndex: Int? = null
    ): Call<MatchList>
}