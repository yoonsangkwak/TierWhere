package site.yoonsang.tierwhere.src.main.history.summoner

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import site.yoonsang.tierwhere.src.main.history.summoner.model.Summoner

interface HistorySummonerRetrofitInterface {

    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}")
    fun getSummoner(
        @Path("summonerName") summonerName: String
    ): Call<Summoner>
}