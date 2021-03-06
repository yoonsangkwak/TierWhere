package site.yoonsang.tierwhere.src.main.history.summoner

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.history.summoner.model.Summoner

class HistorySummonerService(val view: HistorySummonerView) {

    fun tryGetSummoner(summonerName: String) {
        val historySummonerRetrofitInterface =
            ApplicationClass.sRetrofit.create(HistorySummonerRetrofitInterface::class.java)
        historySummonerRetrofitInterface.getSummoner(summonerName)
            .enqueue(object : Callback<Summoner> {
                override fun onResponse(call: Call<Summoner>, response: Response<Summoner>) {
                    if (response.body() != null) {
                        view.getSummonerSuccess(response.body() as Summoner)
                    } else {
                        view.getSummonerFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<Summoner>, t: Throwable) {
                    view.getSummonerFailure(t.message ?: "통신 오류")
                }
            })
    }
}