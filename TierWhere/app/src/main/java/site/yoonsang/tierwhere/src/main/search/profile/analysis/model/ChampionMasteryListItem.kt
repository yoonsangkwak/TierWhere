package site.yoonsang.tierwhere.src.main.search.profile.analysis.model


import com.google.gson.annotations.SerializedName

data class ChampionMasteryListItem(
    @SerializedName("championId")
    val championId: Int,
    @SerializedName("championLevel")
    val championLevel: Int,
    @SerializedName("championPoints")
    val championPoints: Int,
    @SerializedName("championPointsSinceLastLevel")
    val championPointsSinceLastLevel: Int,
    @SerializedName("championPointsUntilNextLevel")
    val championPointsUntilNextLevel: Int,
    @SerializedName("chestGranted")
    val chestGranted: Boolean,
    @SerializedName("lastPlayTime")
    val lastPlayTime: Long,
    @SerializedName("summonerId")
    val summonerId: String,
    @SerializedName("tokensEarned")
    val tokensEarned: Int
)