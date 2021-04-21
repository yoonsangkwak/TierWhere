package site.yoonsang.tierwhere.src.main.search

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import site.yoonsang.tierwhere.src.main.search.model.SearchSummoner
import site.yoonsang.tierwhere.src.main.search.model.SearchSummonerLeague

interface SearchRetrofitInterface {

    @GET("/lol/summoner/v4/summoners/{encryptedSummonerId}")
    fun getSummoner(
        @Path("encryptedSummonerId") summonerId: String
    ):Call<SearchSummoner>

    @GET("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun getUserProfile(
        @Path("encryptedSummonerId") summonerId: String
    ): Call<SearchSummonerLeague>
}