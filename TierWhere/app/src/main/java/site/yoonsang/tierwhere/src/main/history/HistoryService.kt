package site.yoonsang.tierwhere.src.main.history

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.history.model.Summoner

class HistoryService(val view: HistoryView) {

    fun tryGetSummoner(summonerName: String) {
        val historyRetrofitInterface =
            ApplicationClass.sRetrofit.create(HistoryRetrofitInterface::class.java)
        historyRetrofitInterface.getSummoner(summonerName).enqueue(object : Callback<Summoner> {
            override fun onResponse(call: Call<Summoner>, response: Response<Summoner>) {
                if (response.body() != null) {
                    view.getSummonerSuccess(response.body() as Summoner)
                } else {
                    when (response.code()) {
                        404 -> view.getSummonerFailure("존재하지 않는 소환사 입니다.")
                        else -> view.getSummonerFailure("잘못된 요청입니다.")
                    }
                }
            }

            override fun onFailure(call: Call<Summoner>, t: Throwable) {
                view.getSummonerFailure(t.message ?: "통신 오류")
            }
        })
    }
}