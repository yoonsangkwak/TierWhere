package site.yoonsang.tierwhere.src.main.history.summoner

import site.yoonsang.tierwhere.src.main.history.summoner.model.Summoner

interface HistorySummonerView {

    fun getSummonerSuccess(response: Summoner)

    fun getSummonerFailure(message: String)
}