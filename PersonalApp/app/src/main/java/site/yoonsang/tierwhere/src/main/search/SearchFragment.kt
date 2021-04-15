package site.yoonsang.tierwhere.src.main.search

import android.os.Bundle
import android.view.View
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseFragment
import site.yoonsang.tierwhere.databinding.FragmentSearchBinding
import site.yoonsang.tierwhere.src.main.search.model.GetSummonerResponse

class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search),
    SearchView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchSearchButton.setOnClickListener {
            showLoadingDialog(context!!)
            val summonerName = binding.searchEditText.text.toString()
            SearchService(this).tryGetSummoner(summonerName)
        }
    }

    override fun getSummonerSuccess(response: GetSummonerResponse) {
        dismissLoadingDialog()
        showCustomToast(response.summonerLevel.toString())
    }

    override fun getSummonerFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}