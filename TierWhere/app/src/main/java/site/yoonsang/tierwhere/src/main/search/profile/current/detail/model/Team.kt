package site.yoonsang.tierwhere.src.main.search.profile.current.detail.model


import com.google.gson.annotations.SerializedName

data class Team(
    @SerializedName("bans")
    val bans: List<Any>,
    @SerializedName("baronKills")
    val baronKills: Int,
    @SerializedName("dominionVictoryScore")
    val dominionVictoryScore: Int,
    @SerializedName("dragonKills")
    val dragonKills: Int,
    @SerializedName("firstBaron")
    val firstBaron: Boolean,
    @SerializedName("firstBlood")
    val firstBlood: Boolean,
    @SerializedName("firstDragon")
    val firstDragon: Boolean,
    @SerializedName("firstInhibitor")
    val firstInhibitor: Boolean,
    @SerializedName("firstRiftHerald")
    val firstRiftHerald: Boolean,
    @SerializedName("firstTower")
    val firstTower: Boolean,
    @SerializedName("inhibitorKills")
    val inhibitorKills: Int,
    @SerializedName("riftHeraldKills")
    val riftHeraldKills: Int,
    @SerializedName("teamId")
    val teamId: Int,
    @SerializedName("towerKills")
    val towerKills: Int,
    @SerializedName("vilemawKills")
    val vilemawKills: Int,
    @SerializedName("win")
    val win: String
)