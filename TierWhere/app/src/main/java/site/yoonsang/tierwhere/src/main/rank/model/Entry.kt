package site.yoonsang.tierwhere.src.main.rank.model


import com.google.gson.annotations.SerializedName

data class Entry(
    @SerializedName("freshBlood")
    val freshBlood: Boolean,
    @SerializedName("hotStreak")
    val hotStreak: Boolean,
    @SerializedName("inactive")
    val inactive: Boolean,
    @SerializedName("leaguePoints")
    val leaguePoints: Int,
    @SerializedName("losses")
    val losses: Int,
    @SerializedName("rank")
    val rank: String,
    @SerializedName("summonerId")
    val summonerId: String,
    @SerializedName("summonerName")
    val summonerName: String,
    @SerializedName("veteran")
    val veteran: Boolean,
    @SerializedName("wins")
    val wins: Int
)