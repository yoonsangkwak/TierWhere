package site.yoonsang.tierwhere.src.main.search.profile.current

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.profile.current.model.DetailMatchInfo

class MatchInfoService(val view: MatchInfoView) {

    fun tryGetDetailMatchInfo(matchId: Long, holder: CurrentMatchListAdapter.ViewHolder) {
        val detailMatchInfoRetrofitInterface =
            ApplicationClass.sRetrofit.create(MatchInfoRetrofitInterface::class.java)
        detailMatchInfoRetrofitInterface.getDetailMatchInfo(matchId)
            .enqueue(object : Callback<DetailMatchInfo> {
                override fun onResponse(
                    call: Call<DetailMatchInfo>,
                    response: Response<DetailMatchInfo>
                ) {
                    if (response.body() != null) {
                        view.getDetailMatchInfoSuccess(response.body() as DetailMatchInfo, holder)
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