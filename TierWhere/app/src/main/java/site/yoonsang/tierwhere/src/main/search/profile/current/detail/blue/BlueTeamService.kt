package site.yoonsang.tierwhere.src.main.search.profile.current.detail.blue

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.blue.model.BlueSummonerTier

class BlueTeamService(val view: BlueTeamView) {

    fun tryGetSummonerTier(summonerId: String, holder: BlueTeamAdapter.ViewHolder) {
        val blueTeamRetrofitInterface = ApplicationClass.sRetrofit.create(BlueTeamRetrofitInterface::class.java)
        blueTeamRetrofitInterface.getSummonerTier(summonerId).enqueue(object : Callback<BlueSummonerTier> {
            override fun onResponse(call: Call<BlueSummonerTier>, response: Response<BlueSummonerTier>) {
                if (response.body() != null) {
                    view.getSummonerTierSuccess(response.body() as BlueSummonerTier, holder)
                } else {
                    view.getSummonerTierFailure(response.message())
                }
            }

            override fun onFailure(call: Call<BlueSummonerTier>, t: Throwable) {
                view.getSummonerTierFailure(t.message ?: "통신 오류")
            }
        })
    }
}