package site.yoonsang.personalapp.src.main.search

import android.os.Bundle
import android.view.View
import site.yoonsang.personalapp.R
import site.yoonsang.personalapp.config.BaseFragment
import site.yoonsang.personalapp.databinding.FragmentSearchBinding

class SearchFragment :
    BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::bind, R.layout.fragment_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}