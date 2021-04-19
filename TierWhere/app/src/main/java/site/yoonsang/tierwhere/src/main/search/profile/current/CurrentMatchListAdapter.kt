package site.yoonsang.tierwhere.src.main.search.profile.current

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.databinding.ItemCurrentMatchBinding
import site.yoonsang.tierwhere.src.main.search.profile.current.model.DetailMatchInfo
import site.yoonsang.tierwhere.src.main.search.profile.model.MatchListItem

class CurrentMatchListAdapter(
    context: Context,
    private val summonerName: String,
    private val data: List<MatchListItem>
) : RecyclerView.Adapter<CurrentMatchListAdapter.ViewHolder>(), DetailMatchInfoView {

    private val inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var binding: ItemCurrentMatchBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val matchSort = binding.matchSortText
        val date = binding.matchDateText
        val result = binding.matchResultText
        val resultLayout = binding.matchResultLayout
        val playTime = binding.matchPlayTimeText
        val champion = binding.matchChampionImage
        val spellD = binding.matchSpellD
        val spellF = binding.matchSpellF
        val mainRune = binding.matchRuneMain
        val subRune = binding.matchRuneSub
        val kdaRate = binding.matchKdaRateText
        val kill = binding.matchKillText
        val death = binding.matchDeathText
        val assist = binding.matchAssistText
        val itemOne = binding.matchItemOneImage
        val itemTwo = binding.matchItemTwoImage
        val itemThree = binding.matchItemThreeImage
        val itemFour = binding.matchItemFourImage
        val itemFive = binding.matchItemFiveImage
        val itemSix = binding.matchItemSixImage
        val wad = binding.matchItemWad
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemCurrentMatchBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        DetailMatchInfoService(this).tryGetDetailMatchInfo(data[position].gameId, holder)
    }

    override fun getItemCount(): Int = data.size

    override fun getDetailMatchInfoSuccess(
        response: DetailMatchInfo,
        holder: ViewHolder
    ) {
        holder.matchSort.text = when (response.queueId) {
            420 -> "솔로 랭크"
            440 -> "자유 랭크"
            450 -> "칼바람 나락"
            900 -> "URF"
            1020 -> "단일 모드"
            else -> "사용자 설정 게임"
        }
        holder.date.text = calculateDate(response.gameCreation)
        holder.playTime.text = "${response.gameDuration / 60}:${response.gameDuration % 60}"
        var participantId = 0
        for (user in response.participantIdentities) {
            if (user.player.summonerName == summonerName) {
                participantId = user.participantId
            }
        }
        if (response.participants[participantId - 1].stats.win) {
            holder.resultLayout.setBackgroundResource(R.color.win)
            holder.result.text = "승"
        } else {
            holder.resultLayout.setBackgroundResource(R.color.lost)
            holder.result.text = "패"
        }
        Glide.with(holder.itemView.context)
            .load("https://ddragon.leagueoflegends.com/cdn/11.8.1/img/champion/${response.participants[participantId - 1].championId}.png")
            .into(holder.champion)
        Glide.with(holder.itemView.context)
            .load("https://ddragon.leagueoflegends.com/cdn/11.8.1/img/spell/${response.participants[participantId - 1].spell1Id}.png")
            .into(holder.spellD)
        Glide.with(holder.itemView.context)
            .load("https://ddragon.leagueoflegends.com/cdn/11.8.1/img/spell/${response.participants[participantId - 1].spell2Id}.png")
            .into(holder.spellF)
        Glide.with(holder.itemView.context)
            .load("https://ddragon.leagueoflegends.com/cdn/img/perk-images/Styles/${response.participants[participantId - 1].stats.perkPrimaryStyle}.png")
            .into(holder.mainRune)
        Glide.with(holder.itemView.context)
            .load("https://ddragon.leagueoflegends.com/cdn/img/perk-images/Styles/${response.participants[participantId - 1].stats.perkSubStyle}.png")
            .into(holder.subRune)
        val kill = response.participants[participantId - 1].stats.kills
        val death = response.participants[participantId - 1].stats.deaths
        val assist = response.participants[participantId - 1].stats.assists
        holder.kill.text = kill.toString()
        holder.death.text = death.toString()
        holder.assist.text = assist.toString()
        var kda = "0.00"
        if (death != 0) {
            kda = "%.2f".format((kill.toDouble() + assist.toDouble()) / death.toDouble())
        } else if (kill != 0 && assist != 0) {
            kda = "Perfect"
        }
        holder.kdaRate.text = "$kda 평점"
        setItemImage(holder.itemOne, response.participants[participantId - 1].stats.item0)
        setItemImage(holder.itemTwo, response.participants[participantId - 1].stats.item1)
        setItemImage(holder.itemThree, response.participants[participantId - 1].stats.item2)
        setItemImage(holder.itemFour, response.participants[participantId - 1].stats.item3)
        setItemImage(holder.itemFive, response.participants[participantId - 1].stats.item4)
        setItemImage(holder.itemSix, response.participants[participantId - 1].stats.item5)
        Glide.with(holder.wad.context)
            .load("https://ddragon.leagueoflegends.com/cdn/11.8.1/img/item/${response.participants[participantId - 1].stats.item6}.png")
            .into(holder.wad)
    }

    override fun getDetailMatchInfoFailure(message: String) {
    }

    private fun calculateDate(gameDate: Long): String {
        val currentTime = System.currentTimeMillis()
        val diffTime = (currentTime - gameDate) / 1000
        return when {
            diffTime < 60 -> "방금 전"
            (diffTime / 60) < 60 -> "${diffTime / 60}분 전"
            (diffTime / 60 / 60) < 24 -> "${diffTime / 60 / 60}시간 전"
            (diffTime / 60 / 60 / 24) < 30 -> "${diffTime / 60 / 60 / 24}일 전"
            (diffTime / 60 / 60 / 24 / 30) < 12 -> "${diffTime / 60 / 60 / 24 / 30}달 전"
            else -> "${diffTime / 60 / 60 / 24 / 30 / 12}년 전"
        }
    }

    private fun setItemImage(imageView: ImageView, itemId: Int) {
        if (itemId != 0) {
            Glide.with(imageView.context)
                .load("https://ddragon.leagueoflegends.com/cdn/11.8.1/img/item/${itemId}.png")
                .into(imageView)
        }
    }
}