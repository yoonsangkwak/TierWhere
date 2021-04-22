package site.yoonsang.tierwhere.src.main.rank.summoner

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.rank.summoner.model.RankSummoner

class RankSummonerService(val view: RankSummonerView) {

    fun tryGetProfileIcon(summonerName: String, holder: RankSummonerAdapter.ViewHolder) {
        val rankSummonerRetrofitInterface = ApplicationClass.sRetrofit.create(RankSummonerRetrofitInterface::class.java)
        rankSummonerRetrofitInterface.getProfileIcon(summonerName).enqueue(object : Callback<RankSummoner> {
            override fun onResponse(call: Call<RankSummoner>, response: Response<RankSummoner>) {
                if (response.body() != null) {
                    view.getProfileIconSuccess(response.body() as RankSummoner, holder)
                } else {
                    view.getProfileIconFailure(response.message())
                }
            }

            override fun onFailure(call: Call<RankSummoner>, t: Throwable) {
                view.getProfileIconFailure(t.message ?: "통신 오류")
            }
        })
    }
}