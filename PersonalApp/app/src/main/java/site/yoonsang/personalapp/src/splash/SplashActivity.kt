package site.yoonsang.personalapp.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import site.yoonsang.personalapp.config.BaseActivity
import site.yoonsang.personalapp.databinding.ActivitySplashBinding
import site.yoonsang.personalapp.src.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}