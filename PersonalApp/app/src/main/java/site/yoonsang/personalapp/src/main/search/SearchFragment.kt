package site.yoonsang.personalapp.src.main.search

import android.os.Bundle
import android.view.View
import site.yoonsang.personalapp.R
import site.yoonsang.personalapp.config.BaseFragment
import site.yoonsang.personalapp.databinding.FragmentSearchBinding
import site.yoonsang.personalapp.src.main.search.model.GetSummonerResponse

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