package site.yoonsang.tierwhere.src.main.history.summoner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import site.yoonsang.tierwhere.data.DBHelper
import site.yoonsang.tierwhere.data.DB_NAME
import site.yoonsang.tierwhere.data.DB_VERSION
import site.yoonsang.tierwhere.data.HistorySummoner
import site.yoonsang.tierwhere.databinding.ItemHistorySummonerBinding

class HistorySummonerAdapter(
    val context: Context,
    val data: ArrayList<HistorySummoner>
): RecyclerView.Adapter<HistorySummonerAdapter.ViewHolder>() {

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
        Glide.with(holder.profileIcon.context)
            .load("http://ddragon.leagueoflegends.com/cdn/10.18.1/img/profileicon/${data[position].profileIcon}.png")
            .into(holder.profileIcon)
        holder.name.text = data[position].name
        when (data[position].tier) {
            "MASTER",
            "GRANDMASTER",
            "CHALLERNGER" -> holder.tier.text = data[position].tier
            else -> holder.tier.text = "${data[position].tier} ${data[position].rank}"
        }
        holder.delete.setOnClickListener {
            val dbHelper = DBHelper(context, DB_NAME, DB_VERSION)
            dbHelper.deleteHistorySummoner(data[position])
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int = data.size
}