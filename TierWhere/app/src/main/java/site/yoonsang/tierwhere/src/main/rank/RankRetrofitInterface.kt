package site.yoonsang.tierwhere.src.main.rank

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import site.yoonsang.tierwhere.src.main.rank.model.ChallengerLeague
import site.yoonsang.tierwhere.src.main.rank.model.GrandmasterLeague

interface RankRetrofitInterface {

    @GET("/lol/league/v4/challengerleagues/by-queue/{queue}")
    fun getChallengers(
        @Path("queue") queue: String
    ): Call<ChallengerLeague>

    @GET("/lol/league/v4/grandmasterleagues/by-queue/{queue}")
    fun getGrandmasters(
        @Path("queue") queue: String
    ): Call<GrandmasterLeague>
}