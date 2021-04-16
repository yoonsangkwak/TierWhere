package site.yoonsang.tierwhere.src.main.search.profile

import site.yoonsang.tierwhere.src.main.search.profile.model.MatchList
import site.yoonsang.tierwhere.src.main.search.profile.model.SummonerLeague

interface ProfileView {

    fun getUserProfileSuccess(response: SummonerLeague)

    fun getUserProfileFailure(message: String)

    fun getMatchesInfoSuccess(response: MatchList)

    fun getMatchesInfoFailure(message: String)
}