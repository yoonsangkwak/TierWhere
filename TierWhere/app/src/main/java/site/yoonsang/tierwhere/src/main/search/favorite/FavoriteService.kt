package site.yoonsang.tierwhere.src.main.search.favorite

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.src.main.search.favorite.model.FavoriteSummoner

class FavoriteService(val view: FavoriteView) {

    fun tryGetSummoner(summonerName: String) {
        val favoriteRetrofitInterface = ApplicationClass.sRetrofit.create(FavoriteRetrofitInterface::class.java)
        favoriteRetrofitInterface.getSummoner(summonerName).enqueue(object : Callback<FavoriteSummoner> {
            override fun onResponse(
                call: Call<FavoriteSummoner>,
                response: Response<FavoriteSummoner>
            ) {
                if (response.body() != null) {
                    view.getSummonerSuccess(response.body() as FavoriteSummoner)
                } else {
                    when (response.code()) {
                        404 -> view.getSummonerFailure("존재하지 않는 소환사 입니다.")
                        else -> view.getSummonerFailure("잘못된 요청입니다.")
                    }
                }
            }

            override fun onFailure(call: Call<FavoriteSummoner>, t: Throwable) {
                view.getSummonerFailure(t.message ?: "통신 오류")
            }
        })
    }
}