package site.yoonsang.tierwhere.src.main.search.profile.analysis

import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.ChampionMasteryList
import site.yoonsang.tierwhere.src.main.search.profile.analysis.model.CurrentMatches

interface AnalysisView {

    fun getMatchListSuccess(response: CurrentMatches)

    fun getMatchListFailure(message: String)

    fun getChampionMasteryListSuccess(response: ChampionMasteryList)

    fun getChampionMasteryListFailure(message: String)
}