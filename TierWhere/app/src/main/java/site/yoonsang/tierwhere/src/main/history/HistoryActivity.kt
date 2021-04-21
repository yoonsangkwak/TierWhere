package site.yoonsang.tierwhere.src.main.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.data.DBHelper
import site.yoonsang.tierwhere.data.DB_NAME
import site.yoonsang.tierwhere.data.DB_VERSION
import site.yoonsang.tierwhere.databinding.ActivityHistoryBinding
import site.yoonsang.tierwhere.src.main.history.model.HistorySummoner
import site.yoonsang.tierwhere.src.main.history.summoner.HistorySummonerAdapter
import site.yoonsang.tierwhere.src.main.search.profile.ProfileActivity
import java.util.*

class HistoryActivity : BaseActivity<ActivityHistoryBinding>(ActivityHistoryBinding::inflate),
    HistoryView {

    private lateinit var historySummonerAdapter: HistorySummonerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.historyEditText.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY)

        val dbHelper = DBHelper(this, DB_NAME, DB_VERSION)
        historySummonerAdapter = HistorySummonerAdapter(this, dbHelper)
        binding.historyRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historySummonerAdapter
        }

        binding.historySearchImage.setOnClickListener {
            showLoadingDialog(this)
            val summonerName =
                binding.historyEditText.text.toString().toLowerCase(Locale.ROOT)
            HistoryService(this).tryGetSummoner(summonerName)
        }

        binding.historyCloseImage.setOnClickListener {
            binding.historyEditText.setText("")
        }
    }

    override fun getSummonerSuccess(response: HistorySummoner) {
        dismissLoadingDialog()
        val intent = Intent(this, ProfileActivity::class.java)
        intent.putExtra("summonerId", response.id)
        intent.putExtra("accountId", response.accountId)
        intent.putExtra("name", response.name)
        intent.putExtra("level", response.summonerLevel)
        intent.putExtra("icon", response.profileIconId)
        startActivity(intent)
    }

    override fun getSummonerFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}