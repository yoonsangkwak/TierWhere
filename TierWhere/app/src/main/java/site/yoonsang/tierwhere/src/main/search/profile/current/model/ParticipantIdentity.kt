package site.yoonsang.tierwhere.src.main.search.profile.current.model


import com.google.gson.annotations.SerializedName

data class ParticipantIdentity(
    @SerializedName("participantId")
    val participantId: Int,
    @SerializedName("player")
    val player: Player
)