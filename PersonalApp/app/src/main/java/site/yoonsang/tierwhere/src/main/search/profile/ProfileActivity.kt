package site.yoonsang.tierwhere.src.main.search.profile

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivityProfileBinding
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
            .load("http://ddragon.leagueoflegends.com/cdn/10.18.1/img/profileicon/${intent.getIntExtra("icon", 0)}.png")
            .into(binding.profileIconImage)

        for (item in response) {
            when (item.queueType) {
                "RANKED_SOLO_5x5" -> {
                    binding.profileSoloRankTierText.text = "${item.tier} ${item.rank}"
                    binding.profileSoloRankLpText.text = "${item.leaguePoints} LP"
                    val winRate = (item.wins / (item.wins + item.losses).toFloat() * 100).toInt()
                    binding.profileSoloRankWinRateText.text =
                        "${item.wins}승 ${item.losses}패 (${winRate}%)"
                    setTierImage(binding.profileSoloRankTierImage, item.tier)
                }
                "RANKED_FLEX_SR" -> {
                    binding.profileFlexRankTierText.text = "${item.tier} ${item.rank}"
                    binding.profileFlexRankLpText.text = "${item.leaguePoints} LP"
                    val winRate = (item.wins / (item.wins + item.losses).toFloat() * 100).toInt()
                    binding.profileFlexRankWinRateText.text =
                        "${item.wins}승 ${item.losses}패 (${winRate}%)"
                    setTierImage(binding.profileFlexRankTierImage, item.tier)
                }
            }
        }
    }

    override fun getUserProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    private fun setTierImage(imageView: ImageView, tier: String) {
        val tierImage = when (tier) {
            "IRON" -> R.drawable.iron
            "BRONZE" -> R.drawable.bronze
            "SILVER" -> R.drawable.silver
            "GOLD" -> R.drawable.gold
            "PLATINUM" -> R.drawable.platinum
            "DIAMOND" -> R.drawable.diamond
            "MASTER" -> R.drawable.master
            "GRANDMASTER" -> R.drawable.grandmaster
            "CHALLENGER" -> R.drawable.challenger
            else -> R.drawable.unranked
        }

        Glide.with(imageView.context)
            .load(tierImage)
            .into(imageView)
    }

    private fun refreshData() {
        val encryptedSummonerId = intent.getStringExtra("id")
        if (encryptedSummonerId != null) {
            showLoadingDialog(this)
            ProfileService(this).tryGetUserProfile(encryptedSummonerId)
        }
    }
}