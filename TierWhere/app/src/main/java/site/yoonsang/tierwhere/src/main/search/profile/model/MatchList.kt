package site.yoonsang.tierwhere.src.main.search.profile.model


import com.google.gson.annotations.SerializedName

data class MatchList(
    @SerializedName("endIndex")
    val endIndex: Int,
    @SerializedName("matches")
    val matchListItems: List<MatchListItem>,
    @SerializedName("startIndex")
    val startIndex: Int,
    @SerializedName("totalGames")
    val totalGames: Int
)