package site.yoonsang.tierwhere.src.main.search.profile

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import site.yoonsang.tierwhere.src.main.search.profile.model.SummonerLeague

interface ProfileRetrofitInterface {

    @GET("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun getUserProfile(
        @Path("encryptedSummonerId") encryptedSummonerId: String
    ): Call<SummonerLeague>
}