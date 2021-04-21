package site.yoonsang.tierwhere.src.main.search.favorite

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import site.yoonsang.tierwhere.R
import site.yoonsang.tierwhere.config.ApplicationClass
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivityFavoriteBinding
import site.yoonsang.tierwhere.src.main.search.favorite.model.FavoriteSummoner
import java.util.*

class FavoriteActivity : BaseActivity<ActivityFavoriteBinding>(ActivityFavoriteBinding::inflate),
    FavoriteView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var okActivate = false

        binding.favoriteSearchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                okActivate = if (s?.isNotEmpty() == true) {
                    binding.favoriteOkButton.setBackgroundResource(R.drawable.ok_button_background)
                    true
                } else {
                    binding.favoriteOkButton.setBackgroundResource(R.drawable.ok_button_unactivated_background)
                    false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.favoriteOkButton.setOnClickListener {
            if (okActivate) {
                showLoadingDialog(this)
                val summonerName = binding.favoriteSearchEditText.text.toString().toLowerCase(Locale.ROOT)
                FavoriteService(this).tryGetSummoner(summonerName)
            }
        }
    }

    override fun getSummonerSuccess(response: FavoriteSummoner) {
        dismissLoadingDialog()
        ApplicationClass.sSharedPreferences.edit().putString(ApplicationClass.FAVORITE, response.id).apply()
        intent.putExtra("name", response.name)
        intent.putExtra("accountId", response.accountId)
        intent.putExtra("summonerId", response.id)
        intent.putExtra("profileIcon", response.profileIconId)
        intent.putExtra("level", response.summonerLevel)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    override fun getSummonerFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}