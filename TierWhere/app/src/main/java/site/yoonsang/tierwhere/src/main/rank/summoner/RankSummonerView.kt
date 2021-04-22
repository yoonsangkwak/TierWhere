package site.yoonsang.tierwhere.src.main.rank.summoner

import site.yoonsang.tierwhere.src.main.rank.summoner.model.RankSummoner

interface RankSummonerView {

    fun getProfileIconSuccess(response: RankSummoner, holder: RankSummonerAdapter.ViewHolder)

    fun getProfileIconFailure(message: String)
}