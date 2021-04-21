package site.yoonsang.tierwhere.src.main.history.summoner

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.data.DBHelper
import site.yoonsang.tierwhere.data.DB_NAME
import site.yoonsang.tierwhere.data.DB_VERSION
import site.yoonsang.tierwhere.data.HistorySummoner
import site.yoonsang.tierwhere.databinding.ItemHistorySummonerBinding
import site.yoonsang.tierwhere.src.main.history.HistoryActivity
import site.yoonsang.tierwhere.src.main.history.summoner.model.Summoner
import site.yoonsang.tierwhere.src.main.search.profile.ProfileActivity

class HistorySummonerAdapter(
    val context: Context,
    private val dbHelper: DBHelper
): RecyclerView.Adapter<HistorySummonerAdapter.ViewHolder>(), HistorySummonerView {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private lateinit var binding: ItemHistorySummonerBinding

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val profileIcon = binding.itemHistoryProfileIconImage
        val name = binding.itemHistorySummonerNameText
        val tier = binding.itemHistoryTierText
        val delete = binding.itemHistoryDeleteImage
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemHistorySummonerBinding.inflate(inflater, parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dbHelper.selectHistorySummoner()
        Glide.with(holder.profileIcon.context)
            .load("http://ddragon.leagueoflegends.com/cdn/11.8.1/img/profileicon/${data[position].profileIcon}.png")
            .placeholder(R.color.iron)
            .error(R.color.iron)
            .into(holder.profileIcon)
        holder.name.text = data[position].name
        when (data[position].tier) {
            "MASTER",
            "GRANDMASTER",
            "CHALLERNGER" -> holder.tier.text = data[position].tier
            else -> holder.tier.text = "${data[position].tier} ${data[position].rank}"
        }
        holder.delete.setOnClickListener {
            dbHelper.deleteHistorySummoner(data[position])
            notifyItemRemoved(position)
        }
        holder.itemView.setOnClickListener {
            (context as HistoryActivity).showLoadingDialog(context)
            HistorySummonerService(this).tryGetSummoner(data[position].name)
        }
    }

    override fun getItemCount(): Int = dbHelper.selectHistorySummoner().size

    override fun getSummonerSuccess(response: Summoner) {
        (context as HistoryActivity).dismissLoadingDialog()
        val intent = Intent(context, ProfileActivity::class.java)
        intent.putExtra("summonerId", response.id)
        intent.putExtra("accountId", response.accountId)
        intent.putExtra("name", response.name)
        intent.putExtra("level", response.summonerLevel)
        intent.putExtra("icon", response.profileIconId)
        context.startActivity(intent)
    }

    override fun getSummonerFailure(message: String) {
        (context as HistoryActivity).dismissLoadingDialog()
        context.showCustomToast(message)
    }
}