package site.yoonsang.tierwhere.src.main.search.profile.current

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.profile.current.model.DetailMatchInfo

class DetailMatchInfoService(val view: DetailMatchInfoView) {

    fun tryGetDetailMatchInfo(matchId: Long, holder: CurrentMatchListAdapter.ViewHolder) {
        val detailMatchInfoRetrofitInterface = ApplicationClass.sRetrofit.create(DetailMatchInfoRetrofitInterface::class.java)
        detailMatchInfoRetrofitInterface.getDetailMatchInfo(matchId).enqueue(object : Callback<DetailMatchInfo> {
            override fun onResponse(
                call: Call<DetailMatchInfo>,
                response: Response<DetailMatchInfo>
            ) {
                view.getDetailMatchInfoSuccess(response.body() as DetailMatchInfo, holder)
            }

            override fun onFailure(call: Call<DetailMatchInfo>, t: Throwable) {
                view.getDetailMatchInfoFailure(t.message ?: "통신 오류")
            }
        })
    }
}