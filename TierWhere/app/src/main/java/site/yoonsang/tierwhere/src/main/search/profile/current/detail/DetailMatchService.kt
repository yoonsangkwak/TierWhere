package site.yoonsang.tierwhere.src.main.search.profile.current.detail

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.model.DetailMatchInfo

class DetailMatchService(val view: DetailMatchView) {

    fun tryGetDetailMatchInfo(matchId: Long) {
        val detailMatchRetrofitInterface = ApplicationClass.sRetrofit.create(DetailMatchRetrofitInterface::class.java)
        detailMatchRetrofitInterface.getDetailMatchInfo(matchId).enqueue(object : Callback<DetailMatchInfo> {
            override fun onResponse(
                call: Call<DetailMatchInfo>,
                response: Response<DetailMatchInfo>
            ) {
                if (response.body() != null) {
                    view.getDetailMatchInfoSuccess(response.body() as DetailMatchInfo)
                } else {
                    view.getDetailMatchInfoFailure(response.message())
                }
            }

            override fun onFailure(call: Call<DetailMatchInfo>, t: Throwable) {
                view.getDetailMatchInfoFailure(t.message ?: "통신 오류")
            }
        })
    }
}