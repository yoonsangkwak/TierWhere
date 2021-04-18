package site.yoonsang.tierwhere.src.main.search.profile.current.model


import com.google.gson.annotations.SerializedName

data class Player(
    @SerializedName("accountId")
    val accountId: String,
    @SerializedName("currentAccountId")
    val currentAccountId: String,
    @SerializedName("currentPlatformId")
    val currentPlatformId: String,
    @SerializedName("matchHistoryUri")
    val matchHistoryUri: String,
    @SerializedName("platformId")
    val platformId: String,
    @SerializedName("profileIcon")
    val profileIcon: Int,
    @SerializedName("summonerId")
    val summonerId: String,
    @SerializedName("summonerName")
    val summonerName: String
)