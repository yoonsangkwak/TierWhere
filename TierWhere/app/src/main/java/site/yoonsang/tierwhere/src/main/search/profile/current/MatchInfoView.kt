package site.yoonsang.tierwhere.src.main.search.profile.current

import site.yoonsang.tierwhere.src.main.search.profile.current.model.DetailMatchInfo

interface MatchInfoView {

    fun getDetailMatchInfoSuccess(response: DetailMatchInfo, holder: CurrentMatchListAdapter.ViewHolder)

    fun getDetailMatchInfoFailure(message: String)
}