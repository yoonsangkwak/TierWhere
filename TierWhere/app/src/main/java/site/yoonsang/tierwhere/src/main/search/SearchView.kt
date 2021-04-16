package site.yoonsang.tierwhere.src.main.search

import site.yoonsang.tierwhere.src.main.search.model.Summoner

interface SearchView {

    fun getSummonerSuccess(response: Summoner)

    fun getSummonerFailure(message: String)
}