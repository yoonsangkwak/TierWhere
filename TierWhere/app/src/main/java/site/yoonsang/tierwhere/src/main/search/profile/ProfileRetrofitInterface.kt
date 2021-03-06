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
        @Path("encryptedSummonerId") summonerId: String
    ): Call<SummonerLeague>

    @GET("/lol/match/v4/matchlists/by-account/{encryptedAccountId}")
    fun getMatchesInfo(
        @Path("encryptedAccountId") accountId: String,
        @Query("endIndex") endIndex: Int?,
        @Query("beginIndex") beginIndex: Int?
    ): Call<MatchList>
}