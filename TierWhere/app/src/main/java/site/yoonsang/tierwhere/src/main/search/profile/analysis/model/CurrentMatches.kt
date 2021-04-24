package site.yoonsang.tierwhere.src.main.search.profile.analysis.model


import com.google.gson.annotations.SerializedName

data class CurrentMatches(
    @SerializedName("endIndex")
    val endIndex: Int,
    @SerializedName("matches")
    val matches: List<Match>,
    @SerializedName("startIndex")
    val startIndex: Int,
    @SerializedName("totalGames")
    val totalGames: Int
)