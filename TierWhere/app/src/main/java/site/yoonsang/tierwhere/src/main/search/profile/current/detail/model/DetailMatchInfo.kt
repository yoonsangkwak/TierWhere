package site.yoonsang.tierwhere.src.main.search.profile.current.detail.model


import com.google.gson.annotations.SerializedName

data class DetailMatchInfo(
    @SerializedName("gameCreation")
    val gameCreation: Long,
    @SerializedName("gameDuration")
    val gameDuration: Int,
    @SerializedName("gameId")
    val gameId: Long,
    @SerializedName("gameMode")
    val gameMode: String,
    @SerializedName("gameType")
    val gameType: String,
    @SerializedName("gameVersion")
    val gameVersion: String,
    @SerializedName("mapId")
    val mapId: Int,
    @SerializedName("participantIdentities")
    val participantIdentities: List<ParticipantIdentity>,
    @SerializedName("participants")
    val participants: List<Participant>,
    @SerializedName("platformId")
    val platformId: String,
    @SerializedName("queueId")
    val queueId: Int,
    @SerializedName("seasonId")
    val seasonId: Int,
    @SerializedName("teams")
    val teams: List<Team>
)