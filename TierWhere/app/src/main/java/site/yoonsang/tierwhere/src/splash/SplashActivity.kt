package site.yoonsang.tierwhere.src.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import site.yoonsang.tierwhere.config.BaseActivity
import site.yoonsang.tierwhere.databinding.ActivitySplashBinding
import site.yoonsang.tierwhere.src.main.MainActivity

class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 2000)
    }
}