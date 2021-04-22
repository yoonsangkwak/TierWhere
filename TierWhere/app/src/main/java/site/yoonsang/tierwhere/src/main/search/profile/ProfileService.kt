package site.yoonsang.tierwhere.src.main.search.profile

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.profile.model.MatchList
import site.yoonsang.tierwhere.src.main.search.profile.model.SummonerLeague

class ProfileService(val view: ProfileView) {

    fun tryGetUserProfile(summonerId: String) {
        val profileRetrofitInterface =
            ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.getUserProfile(summonerId)
            .enqueue(object : Callback<SummonerLeague> {
                override fun onResponse(
                    call: Call<SummonerLeague>,
                    response: Response<SummonerLeague>
                ) {
                    if (response.body() != null) {
                        view.getUserProfileSuccess(response.body() as SummonerLeague)
                    } else {
                        view.getUserProfileFailure(response.message())
                    }
                }

                override fun onFailure(call: Call<SummonerLeague>, t: Throwable) {
                    view.getUserProfileFailure(t.message ?: "통신 오류")
                }
            })
    }

    fun tryGetMatchList(accountId: String, beginIndex: Int? = null, endIndex: Int? = null) {
        val profileRetrofitInterface =
            ApplicationClass.sRetrofit.create(ProfileRetrofitInterface::class.java)
        profileRetrofitInterface.getMatchesInfo(accountId, endIndex, beginIndex)
            .enqueue(object : Callback<MatchList> {
                override fun onResponse(call: Call<MatchList>, response: Response<MatchList>) {
                    view.getMatchesInfoSuccess(response.body() as MatchList)
                }

                override fun onFailure(call: Call<MatchList>, t: Throwable) {
                    view.getMatchesInfoFailure(t.message ?: "통신 오류")
                }
            })
    }
}