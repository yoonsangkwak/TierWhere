package site.yoonsang.tierwhere.src.main.search

import android.content.Intent
import android.os.Bundle
import android.view.View
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseFragment
import site.yoonsang.tierwhere.databinding.FragmentSearchBinding
import site.yoonsang.tierwhere.src.main.search.model.Summoner
import site.yoonsang.tierwhere.src.main.search.profile.ProfileActivity
import java.util.*

class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search),
    SearchView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchSearchButton.setOnClickListener {
            showLoadingDialog(context!!)
            val summonerName = binding.searchEditText.text.toString().toLowerCase(Locale.ROOT)
            SearchService(this).tryGetSummoner(summonerName)
        }
    }

    override fun getSummonerSuccess(response: Summoner) {
        dismissLoadingDialog()
        showCustomToast(response.name)
        val intent = Intent(activity, ProfileActivity::class.java)
        intent.putExtra("id", response.id)
        intent.putExtra("name", response.name)
        intent.putExtra("date", response.revisionDate)
        intent.putExtra("level", response.summonerLevel)
        startActivity(intent)
    }

    override fun getSummonerFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}