package site.yoonsang.tierwhere.src.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivityMainBinding
import site.yoonsang.tierwhere.src.main.history.HistoryFragment
import site.yoonsang.tierwhere.src.main.rank.RankFragment
import site.yoonsang.tierwhere.src.main.search.SearchFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {

    private val fragmentSearch by lazy { SearchFragment() }
    private val fragmentHistory by lazy { HistoryFragment() }
    private val fragmentRank by lazy { RankFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.mainBottomNavigationView.selectedItemId = R.id.menu_search
        changeFragment(fragmentSearch)

        binding.mainBottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_search -> {
                    changeFragment(fragmentSearch)
                    true
                }
                R.id.menu_history -> {
                    changeFragment(fragmentHistory)
                    true
                }
                R.id.menu_rank -> {
                    changeFragment(fragmentRank)
                    true
                }
                else -> false
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainFragmentContainer.id, fragment)
            .commit()
    }
}