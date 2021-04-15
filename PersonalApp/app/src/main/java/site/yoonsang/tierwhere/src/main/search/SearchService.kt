package site.yoonsang.tierwhere.src.main.search

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.model.Summoner

class SearchService(val view: SearchView) {

    fun tryGetSummoner(summonerName: String) {
        val searchRetrofitInterface =
            ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSummoner(summonerName)
            .enqueue(object : Callback<Summoner> {
                override fun onResponse(
                    call: Call<Summoner>,
                    response: Response<Summoner>
                ) {
                    view.getSummonerSuccess(response.body() as Summoner)
                }

                override fun onFailure(call: Call<Summoner>, t: Throwable) {
                    view.getSummonerFailure(t.message ?: "통신 오류")
                }
            })
    }
}