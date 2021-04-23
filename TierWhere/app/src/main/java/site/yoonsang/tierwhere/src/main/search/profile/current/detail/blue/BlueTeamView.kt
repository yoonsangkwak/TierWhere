package site.yoonsang.tierwhere.src.main.search.profile.current.detail.blue

import site.yoonsang.tierwhere.src.main.search.profile.current.detail.blue.model.BlueSummonerTier

interface BlueTeamView {

    fun getSummonerTierSuccess(response: BlueSummonerTier, holder: BlueTeamAdapter.ViewHolder)

    fun getSummonerTierFailure(message: String)
}