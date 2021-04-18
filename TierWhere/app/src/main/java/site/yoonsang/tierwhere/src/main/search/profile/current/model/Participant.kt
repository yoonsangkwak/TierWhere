package site.yoonsang.tierwhere.src.main.search.profile.current.model


import com.google.gson.annotations.SerializedName

data class Participant(
    @SerializedName("championId")
    val championId: Int,
    @SerializedName("participantId")
    val participantId: Int,
    @SerializedName("spell1Id")
    val spell1Id: Int,
    @SerializedName("spell2Id")
    val spell2Id: Int,
    @SerializedName("stats")
    val stats: Stats,
    @SerializedName("teamId")
    val teamId: Int,
    @SerializedName("timeline")
    val timeline: Timeline
)