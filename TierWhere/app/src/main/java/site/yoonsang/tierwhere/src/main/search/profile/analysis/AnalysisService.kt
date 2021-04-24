package site.yoonsang.tierwhere.src.main.search.profile.analysis

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.ChampionMasteryList
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.CurrentMatches

class AnalysisService(val view: AnalysisView) {

    fun tryGetCurrentMatches(accountId: String, endIndex:Int?=20, beginIndex:Int?=0) {
        val analysisRetrofitInterface = ApplicationClass.sRetrofit.create(AnalysisRetrofitInterface::class.java)
        analysisRetrofitInterface.getMatchList(accountId, endIndex, beginIndex).enqueue(object : Callback<CurrentMatches> {
            override fun onResponse(
                call: Call<CurrentMatches>,
                response: Response<CurrentMatches>
            ) {
                if (response.body() != null) {
                    view.getMatchListSuccess(response.body() as CurrentMatches)
                } else {
                    view.getMatchListFailure(response.message())
                }
            }

            override fun onFailure(call: Call<CurrentMatches>, t: Throwable) {
                view.getMatchListFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetChampionMasteryList(summonerId: String) {
        val analysisRetrofitInterface = ApplicationClass.sRetrofit.create(AnalysisRetrofitInterface::class.java)
        analysisRetrofitInterface.getChampionMastery(summonerId).enqueue(object : Callback<ChampionMasteryList> {
            override fun onResponse(
                call: Call<ChampionMasteryList>,
                response: Response<ChampionMasteryList>
            ) {
                if (response.body() != null) {
                    view.getChampionMasteryListSuccess(response.body() as ChampionMasteryList)
                } else {
                    view.getChampionMasteryListFailure(response.message())
                }
            }

            override fun onFailure(call: Call<ChampionMasteryList>, t: Throwable) {
                view.getChampionMasteryListFailure(t.message ?: "통신 오류")
            }
        })
    }
}