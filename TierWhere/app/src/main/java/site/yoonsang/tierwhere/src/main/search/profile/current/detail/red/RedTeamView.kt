package site.yoonsang.tierwhere.src.main.search.profile.current.detail.red

import site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.model.RedSummonerTier

interface RedTeamView {

    fun getSummonerTierSuccess(response: RedSummonerTier, holder: RedTeamAdapter.ViewHolder)

    fun getSummonerTierFailure(message: String)
}