package site.yoonsang.tierwhere.src.main.rank.summoner

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import site.yoonsang.tierwhere.src.main.rank.summoner.model.RankSummoner

interface RankSummonerRetrofitInterface {

    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}")
    fun getProfileIcon(
        @Path("summonerName") summonerName: String
    ): Call<RankSummoner>
}