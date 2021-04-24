package site.yoonsang.tierwhere.src.main.search.profile.current.detail.red

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.model.RedSummoner
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.model.RedSummonerTier

class RedTeamService(val view: RedTeamView) {

    fun tryGetSummonerTier(summonerId: String, holder: RedTeamAdapter.ViewHolder) {
        val redTeamRetrofitInterface = ApplicationClass.sRetrofit.create(RedTeamRetrofitInterface::class.java)
        redTeamRetrofitInterface.getSummonerTier(summonerId).enqueue(object :
            Callback<RedSummonerTier> {
            override fun onResponse(call: Call<RedSummonerTier>, response: Response<RedSummonerTier>) {
                if (response.body() != null) {
                    view.getSummonerTierSuccess(response.body() as RedSummonerTier, holder)
                } else {
                    view.getSummonerTierFailure(response.message())
                }
            }

            override fun onFailure(call: Call<RedSummonerTier>, t: Throwable) {
                view.getSummonerTierFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetSummoner(summonerName: String) {
        val redTeamRetrofitInterface = ApplicationClass.sRetrofit.create(RedTeamRetrofitInterface::class.java)
        redTeamRetrofitInterface.getSummoner(summonerName).enqueue(object : Callback<RedSummoner> {
            override fun onResponse(call: Call<RedSummoner>, response: Response<RedSummoner>) {
                if (response.body() != null) {
                    view.getSummonerSuccess(response.body() as RedSummoner)
                } else {
                    view.getSummonerFailure(response.message())
                }
            }

            override fun onFailure(call: Call<RedSummoner>, t: Throwable) {
                view.getSummonerFailure(t.message ?: "통신 오류")
            }
        })
    }
}