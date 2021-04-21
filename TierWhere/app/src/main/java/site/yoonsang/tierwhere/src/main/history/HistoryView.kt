package site.yoonsang.tierwhere.src.main.history

import site.yoonsang.tierwhere.src.main.history.model.HistorySummoner

interface HistoryView {

    fun getSummonerSuccess(response: HistorySummoner)

    fun getSummonerFailure(message: String)
}