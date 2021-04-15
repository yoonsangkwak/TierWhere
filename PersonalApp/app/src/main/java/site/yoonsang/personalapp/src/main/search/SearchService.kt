package site.yoonsang.personalapp.src.main.search

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.personalapp.config.ApplicationClass
import site.yoonsang.personalapp.src.main.search.model.GetSummonerResponse

class SearchService(val view: SearchView) {

    fun tryGetSummoner(summonerName: String) {
        val searchRetrofitInterface =
            ApplicationClass.sRetrofit.create(SearchRetrofitInterface::class.java)
        searchRetrofitInterface.getSummoner(summonerName)
            .enqueue(object : Callback<GetSummonerResponse> {
                override fun onResponse(
                    call: Call<GetSummonerResponse>,
                    response: Response<GetSummonerResponse>
                ) {
                    view.getSummonerSuccess(response.body() as GetSummonerResponse)
                }

                override fun onFailure(call: Call<GetSummonerResponse>, t: Throwable) {
                    view.getSummonerFailure(t.message ?: "통신 오류")
                }
            })
    }
}