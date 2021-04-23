package site.yoonsang.tierwhere.src.main.search.profile.current

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import site.yoonsang.tierwhere.src.main.search.profile.current.model.DetailMatchInfo

interface MatchInfoRetrofitInterface {

    @GET("/lol/match/v4/matches/{matchId}")
    fun getDetailMatchInfo(
        @Path("matchId") matchId: Long
    ): Call<DetailMatchInfo>
}