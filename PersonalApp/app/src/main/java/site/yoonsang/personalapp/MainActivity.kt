package site.yoonsang.personalapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import site.yoonsang.personalapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val fragmentSearch by lazy { SearchFragment() }
    private val fragmentHistory by lazy { HistoryFragment() }
    private val fragmentRank by lazy { RankFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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