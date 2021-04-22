package site.yoonsang.tierwhere.src.main.rank

import site.yoonsang.tierwhere.src.main.rank.model.ChallengerLeague
import site.yoonsang.tierwhere.src.main.rank.model.GrandmasterLeague

interface RankView {

    fun getChallengersSuccess(response: ChallengerLeague)

    fun getChallengersFailure(message: String)

    fun getGrandmastersSuccess(response: GrandmasterLeague)

    fun getGrandmastersFailure(message: String)
}