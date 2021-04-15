package site.yoonsang.tierwhere.src.main.search

import site.yoonsang.tierwhere.src.main.search.model.GetSummonerResponse

interface SearchView {

    fun getSummonerSuccess(response: GetSummonerResponse)

    fun getSummonerFailure(message: String)
}