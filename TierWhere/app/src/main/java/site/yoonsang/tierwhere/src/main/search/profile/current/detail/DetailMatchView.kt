package site.yoonsang.tierwhere.src.main.search.profile.current.detail

import site.yoonsang.tierwhere.src.main.search.profile.current.detail.model.DetailMatchInfo

interface DetailMatchView {

    fun getDetailMatchInfoSuccess(response: DetailMatchInfo)

    fun getDetailMatchInfoFailure(message: String)
}