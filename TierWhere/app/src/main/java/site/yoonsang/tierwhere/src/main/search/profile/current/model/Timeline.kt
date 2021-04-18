package site.yoonsang.tierwhere.src.main.search.profile.current.model


import com.google.gson.annotations.SerializedName

data class Timeline(
    @SerializedName("creepsPerMinDeltas")
    val creepsPerMinDeltas: CreepsPerMinDeltas,
    @SerializedName("csDiffPerMinDeltas")
    val csDiffPerMinDeltas: CsDiffPerMinDeltas,
    @SerializedName("damageTakenDiffPerMinDeltas")
    val damageTakenDiffPerMinDeltas: DamageTakenDiffPerMinDeltas,
    @SerializedName("damageTakenPerMinDeltas")
    val damageTakenPerMinDeltas: DamageTakenPerMinDeltas,
    @SerializedName("goldPerMinDeltas")
    val goldPerMinDeltas: GoldPerMinDeltas,
    @SerializedName("lane")
    val lane: String,
    @SerializedName("participantId")
    val participantId: Int,
    @SerializedName("role")
    val role: String,
    @SerializedName("xpDiffPerMinDeltas")
    val xpDiffPerMinDeltas: XpDiffPerMinDeltas,
    @SerializedName("xpPerMinDeltas")
    val xpPerMinDeltas: XpPerMinDeltas
)