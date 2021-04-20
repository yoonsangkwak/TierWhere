package site.yoonsang.tierwhere.src.main.history

import site.yoonsang.tierwhere.src.main.history.model.Summoner

interface HistoryView {

    fun getSummonerSuccess(response: Summoner)

    fun getSummonerFailure(message: String)
}