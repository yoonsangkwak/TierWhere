package site.yoonsang.personalapp.src.main.search

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import site.yoonsang.personalapp.src.main.search.model.GetSummonerResponse

interface SearchRetrofitInterface {

    @GET("/lol/summoner/v4/summoners/by-name/{summonerName}")
    fun getSummoner(
        @Path("summonerName") summonerName: String
    ): Call<GetSummonerResponse>
}