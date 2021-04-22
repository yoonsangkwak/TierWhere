package site.yoonsang.tierwhere.src.main.rank.summoner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.databinding.ItemRankSummonerBinding
import site.yoonsang.tierwhere.src.main.rank.model.Entry
import site.yoonsang.tierwhere.src.main.rank.summoner.model.RankSummoner

class RankSummonerAdapter(
    val context: Context,
    val data: MutableList<Entry>
) : RecyclerView.Adapter<RankSummonerAdapter.ViewHolder>(), RankSummonerView {

    private val inflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding: ItemRankSummonerBinding

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ranking = binding.itemRankRankingText
        val profileIcon = binding.itemRankProfileIconImage
        val summonerName = binding.itemRankSummonerNameText
        val tier = binding.itemRankTierText
        val lp = binding.itemRankLpText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemRankSummonerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.ranking.text = (position + 1).toString()
        holder.summonerName.text = data[position].summonerName
        if (position < 300) {
            holder.tier.text = "CHALLENGER"
            holder.tier.setTextColor(context.getColor(R.color.challenger))
        } else {
            holder.tier.text = "GRANDMASTER"
            holder.tier.setTextColor(context.getColor(R.color.grandmaster))
        }
        holder.lp.text = "${data[position].leaguePoints} LP"
        RankSummonerService(this).tryGetProfileIcon(data[position].summonerName, holder)
    }

    override fun getItemCount(): Int = data.size

    override fun getProfileIconSuccess(response: RankSummoner, holder: ViewHolder) {
        Glide.with(holder.profileIcon.context)
            .load("http://ddragon.leagueoflegends.com/cdn/11.8.1/img/profileicon/${response.profileIconId}.png")
            .into(holder.profileIcon)
    }

    override fun getProfileIconFailure(message: String) {

    }
}