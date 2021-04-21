package site.yoonsang.tierwhere.src.main.search.favorite

import site.yoonsang.tierwhere.src.main.search.favorite.model.FavoriteSummoner

interface FavoriteView {

    fun getSummonerSuccess(response: FavoriteSummoner)

    fun getSummonerFailure(message: String)
}