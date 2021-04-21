package site.yoonsang.tierwhere.src.main.search

import site.yoonsang.tierwhere.src.main.search.model.SearchSummonerLeague
import site.yoonsang.tierwhere.src.main.search.model.SearchSummoner

interface SearchView {

    fun getSummonerSuccess(response: SearchSummoner)

    fun getSummonerFailure(message: String)

    fun getUserProfileSuccess(response: SearchSummonerLeague)

    fun getUserProfileFailure(message: String)
}