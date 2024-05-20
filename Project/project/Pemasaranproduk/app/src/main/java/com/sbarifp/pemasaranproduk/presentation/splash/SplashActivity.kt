package com.sbarifp.pemasaranproduk.presentation.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.sbarifp.pemasaranproduk.R
import com.sbarifp.pemasaranproduk.data.hawkstorage.HawkStorage
import com.sbarifp.pemasaranproduk.presentation.login.LoginActivity
import com.sbarifp.pemasaranproduk.presentation.main.MainActivity
import com.sbarifp.pemasaranproduk.utils.startActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        delayAndGoToLogin()
    }

    private fun delayAndGoToLogin() {
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity<LoginActivity>()
            finish()
        }, 2000)
    }

    private fun checkIsLogin(){
        val isLogin = HawkStorage.instance(this).isLogin()
        if (isLogin){
            startActivity<MainActivity>()
            finish()
        }else{
            startActivity<LoginActivity>()
            finish()
        }
    }
}