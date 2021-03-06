package site.yoonsang.tierwhere.src.main.search.profile.current.detail.red

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.model.RedSummoner
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.model.RedSummonerTier

interface RedTeamRetrofitInterface {

    @GET("/lol/league/v4/entries/by-summoner/{encryptedSummonerId}")
    fun getSummonerTier(
        @Path("encryptedSummonerId") summonerId: String
    ): Call<RedSummonerTier>

    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}")
    fun getSummoner(
        @Path("summonerName") summonerName: String
    ): Call<RedSummoner>
}