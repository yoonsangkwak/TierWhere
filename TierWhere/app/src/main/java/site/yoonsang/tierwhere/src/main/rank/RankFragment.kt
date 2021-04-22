package site.yoonsang.tierwhere.src.main.rank

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseFragment
import site.yoonsang.tierwhere.databinding.FragmentRankBinding
import site.yoonsang.tierwhere.src.main.rank.model.ChallengerLeague
import site.yoonsang.tierwhere.src.main.rank.model.Entry
import site.yoonsang.tierwhere.src.main.rank.model.GrandmasterLeague
import site.yoonsang.tierwhere.src.main.rank.summoner.RankSummonerAdapter

class RankFragment : BaseFragment<FragmentRankBinding>(FragmentRankBinding::bind, R.layout.fragment_rank), RankView {

    private val data = mutableListOf<Entry>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(context!!)
        RankService(this).tryGetChallengers()

//        binding.rankRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                if (!recyclerView.canScrollVertically(1)) {
//                    showLoadingDialog(context!!)
//                    RankService(this@RankFragment).tryGetGrandmasters()
//                }
//            }
//        })
    }

    override fun getChallengersSuccess(response: ChallengerLeague) {
        dismissLoadingDialog()
        val entryList = response.entries.sortedByDescending { it.leaguePoints }
        data.addAll(entryList)
        binding.rankRecyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = RankSummonerAdapter(context, data)
        }
    }

    override fun getChallengersFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun getGrandmastersSuccess(response: GrandmasterLeague) {
        dismissLoadingDialog()
        val entryList = response.entries.sortedByDescending { it.leaguePoints }
        data.addAll(entryList)
        binding.rankRecyclerView.adapter?.notifyItemRangeInserted(300, entryList.size)
    }

    override fun getGrandmastersFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}