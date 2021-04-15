package site.yoonsang.tierwhere.src.main.search.profile

import android.os.Bundle
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivityProfileBinding
import site.yoonsang.tierwhere.src.main.search.profile.model.SummonerLeague

class ProfileActivity : BaseActivity<ActivityProfileBinding>(ActivityProfileBinding::inflate), ProfileView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showLoadingDialog(this)
    }

    override fun getUserProfileSuccess(response: SummonerLeague) {
        dismissLoadingDialog()
    }

    override fun getUserProfileFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}