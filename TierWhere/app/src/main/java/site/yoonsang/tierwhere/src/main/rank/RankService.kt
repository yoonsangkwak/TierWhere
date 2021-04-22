package site.yoonsang.tierwhere.src.main.rank

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.rank.model.ChallengerLeague
import site.yoonsang.tierwhere.src.main.rank.model.GrandmasterLeague

class RankService(val view: RankView) {

    fun tryGetChallengers(queue: String="RANKED_SOLO_5x5") {
        val rankRetrofitInterface = ApplicationClass.sRetrofit.create(RankRetrofitInterface::class.java)
        rankRetrofitInterface.getChallengers(queue).enqueue(object : Callback<ChallengerLeague> {
            override fun onResponse(
                call: Call<ChallengerLeague>,
                response: Response<ChallengerLeague>
            ) {
                if (response.body() != null) {
                    view.getChallengersSuccess(response.body() as ChallengerLeague)
                } else {
                    view.getChallengersFailure(response.message())
                }
            }

            override fun onFailure(call: Call<ChallengerLeague>, t: Throwable) {
                view.getChallengersFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetGrandmasters(queue: String="RANKED_SOLO_5x5") {
        val rankRetrofitInterface = ApplicationClass.sRetrofit.create(RankRetrofitInterface::class.java)
        rankRetrofitInterface.getGrandmasters(queue).enqueue(object : Callback<GrandmasterLeague> {
            override fun onResponse(
                call: Call<GrandmasterLeague>,
                response: Response<GrandmasterLeague>
            ) {
                if (response.body() != null) {
                    view.getGrandmastersSuccess(response.body() as GrandmasterLeague)
                } else {
                    view.getGrandmastersFailure(response.message())
                }
            }

            override fun onFailure(call: Call<GrandmasterLeague>, t: Throwable) {
                view.getGrandmastersFailure(t.message ?: "통신 오류")
            }
        })
    }
}