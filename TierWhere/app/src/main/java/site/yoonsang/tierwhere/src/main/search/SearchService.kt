package site.yoonsang.tierwhere.src.main.search

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.model.SearchSummoner
import site.yoonsang.tierwhere.src.main.search.model.SearchSummonerLeague

class SearchService(val view: SearchView) {

    fun tryGetSummoner(summonerId: String) {
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSummoner(summonerId).enqueue(object : Callback<SearchSummoner> {
            override fun onResponse(
                call: Call<SearchSummoner>,
                response: Response<SearchSummoner>
            ) {
                if (response.body() != null) {
                    view.getSummonerSuccess(response.body() as SearchSummoner)
                } else {
                    when (response.code()) {
                        404 -> view.getSummonerFailure("존재하지 않는 소환사 입니다.")
                        else -> view.getSummonerFailure("잘못된 요청입니다.")
                    }
                }
            }

            override fun onFailure(call: Call<SearchSummoner>, t: Throwable) {
                view.getSummonerFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetSummonerLeague(summonerId: String) {
        val searchRetrofitInterface = ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getUserProfile(summonerId).enqueue(object : Callback<SearchSummonerLeague> {
            override fun onResponse(
                call: Call<SearchSummonerLeague>,
                response: Response<SearchSummonerLeague>
            ) {
                view.getUserProfileSuccess(response.body() as SearchSummonerLeague)
            }

            override fun onFailure(call: Call<SearchSummonerLeague>, t: Throwable) {
                view.getUserProfileFailure(t.message ?: "통신 오류")
            }
        })
    }
}