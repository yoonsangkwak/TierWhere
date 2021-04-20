package site.yoonsang.tierwhere.src.main.search.profile

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivityProfileBinding
import site.yoonsang.tierwhere.src.main.search.profile.current.CurrentMatchListAdapter
import site.yoonsang.tierwhere.src.main.search.profile.model.MatchList
import site.yoonsang.tierwhere.src.main.search.profile.model.SummonerLeague
import java.text.SimpleDateFormat
import java.util.*

class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate),
    ProfileView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        refreshData()

        binding.profileRefreshLayout.setOnClickListener {
            refreshData()
        }
    }

    override fun getUserProfileSuccess(response: SummonerLeague) {
        dismissLoadingDialog()
        binding.profileSummonerNameText.text = intent.getStringExtra("name")
        binding.profileSummonerLevelText.text = intent.getIntExtra("level", 0).toString()
        val sdf = SimpleDateFormat(
            "yyyy-MM-dd HH:mm",
            Locale.KOREA
        ).format(intent.getLongExtra("date", 0))
        binding.profileRefreshDateText.text = sdf
        Glide.with(binding.profileIconImage.context)
            .load(
                "http://ddragon.leagueoflegends.com/cdn/10.18.1/img/profileicon/${
                    intent.getIntExtra(
                        "icon",
                        0
                    )
                }.png"
            )
            .into(binding.profileIconImage)

        for (item in response) {
            when (item.queueType) {
                "RANKED_SOLO_5x5" -> {
                    binding.profileSoloRankTierText.text = "${item.tier} ${item.rank}"
                    binding.profileSoloRankLpText.text = "${item.leaguePoints} LP"
                    val winRate = (item.wins / (item.wins + item.losses).toFloat() * 100).toInt()
                    binding.profileSoloRankWinRateText.text =
                        "${item.wins}승 ${item.losses}패 (${winRate}%)"
                    setTier(binding.profileSoloRankTierImage, binding.profileSoloRankTierText, item.tier)
                }
                "RANKED_FLEX_SR" -> {
                    binding.profileFlexRankTierText.text = "${item.tier} ${item.rank}"
                    binding.profileFlexRankLpText.text = "${item.leaguePoints} LP"
                    val winRate = (item.wins / (item.wins + item.losses).toFloat() * 100).toInt()
                    binding.profileFlexRankWinRateText.text =
                        "${item.wins}승 ${item.losses}패 (${winRate}%)"
                    setTier(binding.profileFlexRankTierImage, binding.profileFlexRankTierText, item.tier)
                }
            }
        }
    }

    override fun getUserProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun getMatchesInfoSuccess(response: MatchList) {
        val currentMatchListAdapter = CurrentMatchListAdapter(this, intent.getStringExtra("name")!!, response.matchListItems)
        binding.profileRecordRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currentMatchListAdapter
        }
    }

    override fun getMatchesInfoFailure(message: String) {
        showCustomToast(message)
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
        textView.setTextColor(getColor(tierColor))
    }

    private fun refreshData() {
        val summonerId = intent.getStringExtra("summonerId")
        if (summonerId != null) {
            showLoadingDialog(this)
            ProfileService(this).tryGetUserProfile(summonerId)
            getMatch()
        }
    }

    private fun getMatch() {
        val accountId = intent.getStringExtra("accountId")
        if (accountId != null) {
            ProfileService(this).tryGetMatchList(accountId, 0, 10)
        }
    }
}