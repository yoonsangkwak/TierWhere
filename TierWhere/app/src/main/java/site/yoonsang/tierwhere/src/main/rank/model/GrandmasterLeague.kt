package site.yoonsang.tierwhere.src.main.rank.model


import com.google.gson.annotations.SerializedName

data class GrandmasterLeague(
    @SerializedName("entries")
    val entries: List<Entry>,
    @SerializedName("leagueId")
    val leagueId: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("queue")
    val queue: String,
    @SerializedName("tier")
    val tier: String
)