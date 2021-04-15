package site.yoonsang.personalapp.src.main.search

import site.yoonsang.personalapp.src.main.search.model.GetSummonerResponse

interface SearchView {

    fun getSummonerSuccess(response: GetSummonerResponse)

    fun getSummonerFailure(message: String)
}