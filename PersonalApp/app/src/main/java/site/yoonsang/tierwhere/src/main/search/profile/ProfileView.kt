package site.yoonsang.tierwhere.src.main.search.profile

import site.yoonsang.tierwhere.src.main.search.profile.model.SummonerLeague

interface ProfileView {

    fun getUserProfileSuccess(response: SummonerLeague)

    fun getUserProfileFailure(message: String)
}