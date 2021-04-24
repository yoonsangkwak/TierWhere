package site.yoonsang.tierwhere.src.main.search.profile.current.detail.red

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.databinding.ItemDetailTeamInfoBinding
import site.yoonsang.tierwhere.src.main.search.profile.ProfileActivity
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.model.Participant
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.model.ParticipantIdentity
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.model.RedSummoner
import site.yoonsang.tierwhere.src.main.search.profile.current.detail.red.model.RedSummonerTier
import java.io.BufferedReader
import java.io.InputStreamReader
import java.text.DecimalFormat
import kotlin.math.round

class RedTeamAdapter(
    val context: Context,
    private val player: MutableList<Participant>,
    private val summoner: MutableList<ParticipantIdentity>,
    private val playTime: Int,
    private val summonerName: String,
    private val maxDamage: Int
) : RecyclerView.Adapter<RedTeamAdapter.ViewHolder>(), RedTeamView {

    private val inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding: ItemDetailTeamInfoBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val myBackground = binding.itemTeamBackground
        val championImage = binding.itemTeamChampionImage
        val championLevel = binding.itemTeamChampionLevelText
        val spellD = binding.itemTeamSpellD
        val spellF = binding.itemTeamSpellF
        val runeMain = binding.itemTeamRuneMain
        val runeSub = binding.itemTeamRuneSub
        val tier = binding.itemTeamTierText
        val name = binding.itemTeamSummonerNameText
        val kill = binding.itemTeamKillText
        val death = binding.itemTeamDeathText
        val assist = binding.itemTeamAssistText
        val cs = binding.itemTeamCsText
        val csPerMin = binding.itemTeamCsPerMinText
        val item1 = binding.itemTeamItem1Image
        val item2 = binding.itemTeamItem2Image
        val item3 = binding.itemTeamItem3Image
        val item4 = binding.itemTeamItem4Image
        val item5 = binding.itemTeamItem5Image
        val item6 = binding.itemTeamItem6Image
        val wad = binding.itemTeamWadImage
        val damageGraph = binding.itemTeamDamageProgress
        val damage = binding.itemTeamDamageText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemDetailTeamInfoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        setChampionImage(holder.championImage, player[position].championId)
        holder.championLevel.text = player[position].stats.champLevel.toString()
        holder.name.text = summoner[position].player.summonerName
        RedTeamService(this).tryGetSummonerTier(summoner[position].player.summonerId, holder)
        setSpellImage(holder.spellD, player[position].spell1Id)
        setSpellImage(holder.spellF, player[position].spell2Id)
        setRuneImage(holder.runeMain, player[position].stats.perk0)
        setRuneImage(holder.runeSub, player[position].stats.perkSubStyle)
        setItemImage(holder.item1, player[position].stats.item0)
        setItemImage(holder.item2, player[position].stats.item1)
        setItemImage(holder.item3, player[position].stats.item2)
        setItemImage(holder.item4, player[position].stats.item3)
        setItemImage(holder.item5, player[position].stats.item4)
        setItemImage(holder.item6, player[position].stats.item5)
        setWadImage(holder.wad, player[position].stats.item6)
        holder.kill.text = player[position].stats.kills.toString()
        holder.death.text = player[position].stats.deaths.toString()
        holder.assist.text = player[position].stats.assists.toString()
        holder.cs.text = player[position].stats.totalMinionsKilled.toString()
        setCsPerMin(holder.csPerMin, player[position].stats.totalMinionsKilled, playTime)
        setTotalDamage(holder.damageGraph, holder.damage, player[position].stats.totalDamageDealtToChampions)
        if (summoner[position].player.summonerName == summonerName) {
            holder.myBackground.setBackgroundColor(context.getColor(R.color.my_background))
        }
        holder.name.setOnClickListener {
            val summonerName = summoner[position].player.summonerName
            RedTeamService(this).tryGetSummoner(summonerName)
        }
    }

    override fun getItemCount(): Int = player.size

    private fun setItemImage(imageView: ImageView, itemId: Int) {
        Glide.with(imageView.context)
            .load("https://ddragon.leagueoflegends.com/cdn/11.8.1/img/item/${itemId}.png")
            .placeholder(R.color.item_blank)
            .error(R.color.item_blank)
            .into(imageView)
    }

    private fun setWadImage(imageView: CircleImageView, itemId: Int) {
        Glide.with(imageView.context)
            .load("https://ddragon.leagueoflegends.com/cdn/11.8.1/img/item/${itemId}.png")
            .placeholder(R.color.item_blank)
            .error(R.color.item_blank)
            .into(imageView)
    }

    private fun setSpellImage(imageView: ImageView, id: Int) {
        val assetManager = context.resources.assets
        val inputStream = assetManager.open("jsons/spell.json")
        val isr = InputStreamReader(inputStream)
        val reader = BufferedReader(isr)

        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(line + "\n")
            line = reader.readLine()
        }
        val jsonData = buffer.toString()

        val jsonObject = JSONObject(jsonData)
        val spellName = jsonObject.getString(id.toString())

        Glide.with(imageView.context)
            .load("http://ddragon.leagueoflegends.com/cdn/11.8.1/img/spell/${spellName}.png")
            .placeholder(R.color.iron)
            .error(R.color.iron)
            .into(imageView)
    }

    private fun setChampionImage(imageView: ImageView, id: Int) {
        val assetManager = context.resources.assets
        val inputStream = assetManager.open("jsons/champion.json")
        val isr = InputStreamReader(inputStream)
        val reader = BufferedReader(isr)

        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(line + "\n")
            line = reader.readLine()
        }
        val jsonData = buffer.toString()

        val jsonObject = JSONObject(jsonData)
        val championName = jsonObject.getString(id.toString())

        Glide.with(imageView.context)
            .load("http://ddragon.leagueoflegends.com/cdn/11.8.1/img/champion/${championName}.png")
            .placeholder(R.color.iron)
            .error(R.color.iron)
            .into(imageView)
    }

    private fun setRuneImage(imageView: CircleImageView, id: Int) {
        val assetManager = context.resources.assets
        val inputStream = assetManager.open("jsons/rune.json")
        val isr = InputStreamReader(inputStream)
        val reader = BufferedReader(isr)

        val buffer = StringBuffer()
        var line = reader.readLine()
        while (line != null) {
            buffer.append(line + "\n")
            line = reader.readLine()
        }
        val jsonData = buffer.toString()

        val jsonObject = JSONObject(jsonData)
        val runeIcon = jsonObject.getString(id.toString())

        Glide.with(imageView.context)
            .load("https://ddragon.leagueoflegends.com/cdn/img/${runeIcon}")
            .placeholder(R.color.iron)
            .error(R.color.iron)
            .into(imageView)
    }

    private fun setTotalDamage(graph: ProgressBar, textView: TextView, damage: Int) {
        graph.max = maxDamage
        graph.progress = damage
        val dec = DecimalFormat("#,###")
        textView.text = dec.format(damage)
    }

    private fun setCsPerMin(textView: TextView, cs: Int, time: Int) {
        val csPerMin = round((cs.toDouble() / time * 60) * 10) / 10
        textView.text = "($csPerMin)"
    }

    private fun convertTier(textView: TextView, tier: String, rank: String) {
        val newRank = when (rank) {
            "I" -> "1"
            "II" -> "2"
            "III" -> "3"
            "IV" -> "4"
            else -> "0"
        }
        when (tier) {
            "IRON" -> {
                textView.text = "I$newRank"
                textView.setBackgroundColor(context.getColor(R.color.iron))
            }
            "BRONZE" -> {
                textView.text = "B$newRank"
                textView.setBackgroundColor(context.getColor(R.color.bronze))
            }
            "SILVER" -> {
                textView.text = "S$newRank"
                textView.setBackgroundColor(context.getColor(R.color.silver))
            }
            "GOLD" -> {
                textView.text = "G$newRank"
                textView.setBackgroundColor(context.getColor(R.color.gold))
            }
            "PLATINUM" -> {
                textView.text = "P$newRank"
                textView.setBackgroundColor(context.getColor(R.color.platinum))
            }
            "DIAMOND" -> {
                textView.text = "D$newRank"
                textView.setBackgroundColor(context.getColor(R.color.diamond))
            }
            "MASTER" -> {
                textView.text = "M"
                textView.setBackgroundColor(context.getColor(R.color.master))
            }
            "GRANDMASTER" -> {
                textView.text = "GM"
                textView.setBackgroundColor(context.getColor(R.color.grandmaster))
            }
            "CHALLENGER" -> {
                textView.text = "C"
                textView.setBackgroundColor(context.getColor(R.color.challenger))
            }
        }

    }

    override fun getSummonerTierSuccess(response: RedSummonerTier, holder: ViewHolder) {
        for (item in response) {
            if (item.queueType == "RANKED_SOLO_5x5") {
                convertTier(holder.tier, item.tier, item.rank)
            }
        }
    }

    override fun getSummonerTierFailure(message: String) {
    }

    override fun getSummonerSuccess(response: RedSummoner) {
        val intent = Intent(context, ProfileActivity::class.java)
        intent.putExtra("summonerId", response.id)
        intent.putExtra("accountId", response.accountId)
        intent.putExtra("name", response.name)
        intent.putExtra("level", response.summonerLevel)
        intent.putExtra("icon", response.profileIconId)
        context.startActivity(intent)
    }

    override fun getSummonerFailure(message: String) {
    }
}