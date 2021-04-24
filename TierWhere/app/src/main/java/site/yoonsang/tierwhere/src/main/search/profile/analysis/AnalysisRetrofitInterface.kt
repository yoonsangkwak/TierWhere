package site.yoonsang.tierwhere.src.main.search.profile.analysis

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.ChampionMasteryList
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.CurrentMatches

interface AnalysisRetrofitInterface {

    @GET("/lol/match/v4/matchlists/by-account/{encryptedAccountId}")
    fun getMatchList(
        @Path("encryptedAccountId") accountId: String,
        @Query("endIndex") endIndex: Int?,
        @Query("beginIndex") beginIndex: Int?
    ): Call<CurrentMatches>

    @GET("/lol/champion-mastery/v4/champion-masteries/by-summoner/{encryptedSummonerId}")
    fun getChampionMastery(
        @Path("encryptedSummonerId") summonerId: String
    ): Call<ChampionMasteryList>
}