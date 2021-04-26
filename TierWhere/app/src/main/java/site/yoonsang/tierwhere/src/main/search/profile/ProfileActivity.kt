package site.yoonsang.tierwhere.src.main.search.profile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.data.DBHelper
import site.yoonsang.tierwhere.data.DB_NAME
import site.yoonsang.tierwhere.data.DB_VERSION
import site.yoonsang.tierwhere.data.HistorySummoner
import site.yoonsang.tierwhere.databinding.ActivityProfileBinding
import site.yoonsang.tierwhere.src.main.history.HistoryActivity
import site.yoonsang.tierwhere.src.main.search.profile.analysis.AnalysisActivity
import site.yoonsang.tierwhere.src.main.search.profile.current.CurrentMatchListAdapter
import site.yoonsang.tierwhere.src.main.search.profile.model.MatchList
import site.yoonsang.tierwhere.src.main.search.profile.model.MatchListItem
import site.yoonsang.tierwhere.src.main.search.profile.model.SummonerLeague
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate),
    ProfileView {

    private var beginIndex = 0
    private var endIndex = 20
    private var data = mutableListOf<MatchListItem>()
    private var tempList = mutableListOf<MatchListItem>()
    private var allList = listOf<MatchListItem>()
    var game = 0
    var win = 0
    var lost = 0
    var kill = 0
    var death = 0
    var assist = 0
    var cs = 0
    var playTime = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        refreshData()

        binding.profileRefreshButton.setOnClickListener {
            refreshData()
        }

        binding.profileSearchImage.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        binding.profileScrollView.setOnScrollChangeListener(NestedScrollView.OnScrollChangeListener { v, _, scrollY, _, _ ->
            if (scrollY == (v.getChildAt(0).measuredHeight - v.measuredHeight)) {
                showLoadingDialog(this)
                addData(allList, beginIndex, endIndex)
                binding.profileRecordRecyclerView.adapter?.notifyItemRangeInserted(beginIndex, tempList.size)
                dismissLoadingDialog()
            }
        })

        binding.profileAnalyzeButton.setOnClickListener {
            val name = intent.getStringExtra("name")!!
            val accountId = intent.getStringExtra("accountId")!!
            val summonerId = intent.getStringExtra("summonerId")!!
            val intent = Intent(this, AnalysisActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("accountId", accountId)
            intent.putExtra("summonerId", summonerId)
            intent.putExtra("game", game)
            intent.putExtra("win", win)
            intent.putExtra("lost", lost)
            intent.putExtra("kill", kill)
            intent.putExtra("death", death)
            intent.putExtra("assist", assist)
            intent.putExtra("cs", cs)
            intent.putExtra("playTime", playTime)
            startActivity(intent)
        }
    }

    override fun getUserProfileSuccess(response: SummonerLeague) {
        binding.profileSummonerNameText.text = intent.getStringExtra("name")
        binding.profileSummonerLevelText.text = intent.getIntExtra("level", 0).toString()
        Glide.with(binding.profileIconImage.context)
            .load(
                "http://ddragon.leagueoflegends.com/cdn/11.8.1/img/profileicon/${
                    intent.getIntExtra(
                        "icon",
                        0
                    )
                }.png"
            )
            .placeholder(R.color.iron)
            .error(R.color.iron)
            .into(binding.profileIconImage)

        for (item in response) {
            when (item.queueType) {
                "RANKED_SOLO_5x5" -> {
                    when (item.tier) {
                        "MASTER",
                        "GRANDMASTER",
                        "CHALLERNGER" -> binding.profileSoloRankTierText.text = item.tier
                        else -> binding.profileSoloRankTierText.text =
                            "${item.tier} ${convertRank(item.rank)}"
                    }
                    binding.profileSoloRankLpText.text = "${item.leaguePoints} LP"
                    val winRate = (item.wins / (item.wins + item.losses).toFloat() * 100).toInt()
                    binding.profileSoloRankWinRateText.text =
                        "${item.wins}승 ${item.losses}패 (${winRate}%)"
                    setTier(
                        binding.profileSoloRankTierImage,
                        binding.profileSoloRankTierText,
                        item.tier
                    )
                }
                "RANKED_FLEX_SR" -> {
                    when (item.tier) {
                        "MASTER",
                        "GRANDMASTER",
                        "CHALLERNGER" -> binding.profileFlexRankTierText.text = item.tier
                        else -> binding.profileFlexRankTierText.text =
                            "${item.tier} ${convertRank(item.rank)}"
                    }
                    binding.profileFlexRankLpText.text = "${item.leaguePoints} LP"
                    val winRate = (item.wins / (item.wins + item.losses).toFloat() * 100).toInt()
                    binding.profileFlexRankWinRateText.text =
                        "${item.wins}승 ${item.losses}패 (${winRate}%)"
                    setTier(
                        binding.profileFlexRankTierImage,
                        binding.profileFlexRankTierText,
                        item.tier
                    )
                }
            }
        }

        val dbHelper = DBHelper(this, DB_NAME, DB_VERSION)
        val summonerId = intent.getStringExtra("summonerId")!!
        val name = intent.getStringExtra("name")!!
        var tier = "UNRANKED"
        var rank = ""
        for (item in response) {
            if (item.queueType == "RANKED_SOLO_5x5") {
                tier = item.tier
                rank = convertRank(item.rank)
            }
        }
        val profileIcon = intent.getIntExtra("icon", 0)
        val historySummoner = HistorySummoner(summonerId, name, tier, rank, profileIcon)
        if (dbHelper.searchHistorySummoner(intent.getStringExtra("summonerId")!!) != null) {
            dbHelper.updateHistorySummoner(historySummoner)
        } else {
            dbHelper.insertHistorySummoner(historySummoner)
        }
    }

    override fun getUserProfileFailure(message: String) {
        showCustomToast(message)
    }

    override fun getMatchesInfoSuccess(response: MatchList) {
        dismissLoadingDialog()
        allList = response.matchListItems
        addData(allList, beginIndex, endIndex)
        val currentMatchListAdapter =
            CurrentMatchListAdapter(
                this,
                intent.getStringExtra("name")!!,
                data
            )
        binding.profileRecordRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = currentMatchListAdapter
        }
    }

    override fun getMatchesInfoFailure(message: String) {
        dismissLoadingDialog()
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

    private fun convertRank(rank: String): String {
        return when (rank) {
            "I" -> "1"
            "II" -> "2"
            "III" -> "3"
            "IV" -> "4"
            else -> ""
        }
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
            ProfileService(this).tryGetMatchList(accountId)
        }
    }

    private fun addData(list: List<MatchListItem>, begin: Int, end: Int) {
        if (endIndex < 100) {
            tempList.clear()
            if (end < list.size) {
                for (i in begin until end) {
                    tempList.add(list[i])
                }
                beginIndex += 20
                endIndex += 20
            } else {
                for (i in begin until list.size) {
                    tempList.add(list[i])
                }
            }
            data.addAll(tempList)
        } else {
            showCustomToast("마지막 전적입니다")
        }
    }
}