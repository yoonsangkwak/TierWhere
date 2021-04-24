package site.yoonsang.tierwhere.src.main.search.profile.analysis.model


import com.google.gson.annotations.SerializedName

data class Match(
    @SerializedName("champion")
    val champion: Int,
    @SerializedName("gameId")
    val gameId: Long,
    @SerializedName("lane")
    val lane: String,
    @SerializedName("platformId")
    val platformId: String,
    @SerializedName("queue")
    val queue: Int,
    @SerializedName("role")
    val role: String,
    @SerializedName("season")
    val season: Int,
    @SerializedName("timestamp")
    val timestamp: Long
)