package site.yoonsang.tierwhere.src.main.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.config.BaseFragment
import site.yoonsang.tierwhere.databinding.FragmentSearchBinding
import site.yoonsang.tierwhere.src.main.history.HistoryActivity
import site.yoonsang.tierwhere.src.main.search.favorite.FavoriteActivity
import site.yoonsang.tierwhere.src.main.search.model.SearchSummoner
import site.yoonsang.tierwhere.src.main.search.model.SearchSummonerLeague

class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search),
    SearchView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchSearchLayout.setOnClickListener {
            startActivity(Intent(activity, HistoryActivity::class.java))
        }

        binding.searchFavoriteAbsentLayout.setOnClickListener {
            val intent = Intent(activity, FavoriteActivity::class.java)
            startActivityForResult(intent, 100)
        }

        if (ApplicationClass.sSharedPreferences.getString(
                ApplicationClass.FAVORITE,
                null
            ) != null
        ) {
            showLoadingDialog(context!!)
            SearchService(this).tryGetSummoner(
                ApplicationClass.sSharedPreferences.getString(
                    ApplicationClass.FAVORITE,
                    null
                )!!
            )
            binding.searchFavoriteAbsentLayout.visibility = View.GONE
            binding.searchFavoriteLayout.visibility = View.VISIBLE
        }

        binding.searchFavoriteDelete.setOnClickListener {
            ApplicationClass.sSharedPreferences.edit().putString(
                ApplicationClass.FAVORITE, null
            ).apply()
            binding.searchFavoriteAbsentLayout.visibility = View.VISIBLE
            binding.searchFavoriteLayout.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    binding.searchFavoriteAbsentLayout.visibility = View.GONE
                    binding.searchFavoriteLayout.visibility = View.VISIBLE
                    showLoadingDialog(context!!)
                    SearchService(this).tryGetSummonerLeague(data?.getStringExtra("summonerId")!!)
                    binding.searchFavoriteSummonerNameText.text = data.getStringExtra("name")
                    Glide.with(binding.searchFavoriteProfileIconImage.context)
                        .load(
                            "http://ddragon.leagueoflegends.com/cdn/11.8.1/img/profileicon/${
                                data.getIntExtra(
                                    "profileIcon",
                                    0
                                )
                            }.png"
                        )
                        .placeholder(R.color.iron)
                        .error(R.color.iron)
                        .into(binding.searchFavoriteProfileIconImage)
                }
            }
        }
    }

    override fun getUserProfileSuccess(response: SearchSummonerLeague) {
        dismissLoadingDialog()
        binding.searchFavoriteTierText.text = "Unranked"
        binding.searchFavoriteLpText.text = ""
        binding.searchFavoriteWinRateText.text = "0승 0패 (0%)"
        binding.searchFavoriteTierText.setTextColor(context!!.getColor(R.color.black))
        Glide.with(binding.searchFavoriteTierImage.context)
            .load(R.drawable.unranked)
            .into(binding.searchFavoriteTierImage)
        for (item in response) {
            if (item.queueType == "RANKED_SOLO_5x5") {
                when (item.tier) {
                    "MASTER",
                    "GRANDMASTER",
                    "CHALLERNGER" -> binding.searchFavoriteTierText.text = item.tier
                    else -> binding.searchFavoriteTierText.text =
                        "${item.tier} ${convertRank(item.rank)}"
                }
                setTier(
                    binding.searchFavoriteTierImage,
                    binding.searchFavoriteTierText,
                    item.tier
                )
                binding.searchFavoriteLpText.text = "${item.leaguePoints} LP"
                val winRate = (item.wins / (item.wins + item.losses).toFloat() * 100).toInt()
                binding.searchFavoriteWinRateText.text =
                    "${item.wins}승 ${item.losses}패 (${winRate}%)"
            }
        }
    }

    override fun getUserProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun getSummonerSuccess(response: SearchSummoner) {
        SearchService(this).tryGetSummonerLeague(response.id)
        binding.searchFavoriteSummonerNameText.text = response.name
        Glide.with(binding.searchFavoriteProfileIconImage.context)
            .load("http://ddragon.leagueoflegends.com/cdn/11.8.1/img/profileicon/${response.profileIconId}.png")
            .placeholder(R.color.iron)
            .error(R.color.iron)
            .into(binding.searchFavoriteProfileIconImage)
    }

    override fun getSummonerFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    private fun convertRank(rank: String): String {
        return when (rank) {
            "I" -> "1"
            "II" -> "2"
            "III" -> "3"
            "IV" -> "4"
            else -> ""
        }
    }

    private fun setTier(imageView: ImageView, textView: TextView, tier: String) {
        val tierImage: Int
        val tierColor: Int
        when (tier) {
            "IRON" -> {
                tierImage = R.drawable.iron
                tierColor = R.color.iron
            }
            "BRONZE" -> {
                tierImage = R.drawable.bronze
                tierColor = R.color.bronze
            }
            "SILVER" -> {
                tierImage = R.drawable.silver
                tierColor = R.color.silver
            }
            "GOLD" -> {
                tierImage = R.drawable.gold
                tierColor = R.color.gold
            }
            "PLATINUM" -> {
                tierImage = R.drawable.platinum
                tierColor = R.color.platinum
            }
            "DIAMOND" -> {
                tierImage = R.drawable.diamond
                tierColor = R.color.diamond
            }
            "MASTER" -> {
                tierImage = R.drawable.master
                tierColor = R.color.master
            }
            "GRANDMASTER" -> {
                tierImage = R.drawable.grandmaster
                tierColor = R.color.grandmaster
            }
            "CHALLENGER" -> {
                tierImage = R.drawable.challenger
                tierColor = R.color.challenger
            }
            else -> {
                tierImage = R.drawable.unranked
                tierColor = R.color.black
            }
        }
        Glide.with(imageView.context)
            .load(tierImage)
            .into(imageView)
        textView.setTextColor(context!!.getColor(tierColor))
    }
}