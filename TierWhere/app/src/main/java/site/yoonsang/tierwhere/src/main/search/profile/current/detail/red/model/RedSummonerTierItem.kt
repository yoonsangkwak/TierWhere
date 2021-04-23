package site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.model

import com.google.gson.annotations.SerializedName

data class RedSummonerTierItem(
    @SerializedName("freshBlood")
    val freshBlood: Boolean,
    @SerializedName("hotStreak")
    val hotStreak: Boolean,
    @SerializedName("inactive")
    val inactive: Boolean,
    @SerializedName("leagueId")
    val leagueId: String,
    @SerializedName("leaguePoints")
    val leaguePoints: Int,
    @SerializedName("losses")
    val losses: Int,
    @SerializedName("queueType")
    val queueType: String,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("summonerId")
    val summonerId: String,
    @SerializedName("summonerName")
    val summonerName: String,
    @SerializedName("tier")
    val tier: String,
    @SerializedName("veteran")
    val veteran: Boolean,
    @SerializedName("wins")
    val wins: Int
)