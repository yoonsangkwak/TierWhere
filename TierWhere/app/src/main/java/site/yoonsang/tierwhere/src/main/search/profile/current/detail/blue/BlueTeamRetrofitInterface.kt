package site.yoonsang.tierwhere.src.main.search.profile.current.detail.blue

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.blue.model.BlueSummonerTier

interface BlueTeamRetrofitInterface {

    @GET("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun getSummonerTier(
        @Path("encryptedSummonerId") summonerId: String
    ): Call<BlueSummonerTier>
}