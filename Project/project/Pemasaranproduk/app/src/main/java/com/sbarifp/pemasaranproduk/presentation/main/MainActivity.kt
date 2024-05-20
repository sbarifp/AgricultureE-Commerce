package com.sbarifp.pemasaranproduk.presentation.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sbarifp.pemasaranproduk.BuildConfig
import com.sbarifp.pemasaranproduk.R
import com.sbarifp.pemasaranproduk.databinding.ActivityMainBinding
import com.sbarifp.pemasaranproduk.presentation.home.HomeFragment
import com.sbarifp.pemasaranproduk.presentation.myads.MyAdsFragment
import com.sbarifp.pemasaranproduk.presentation.sell.SellActivity
import com.sbarifp.pemasaranproduk.presentation.user.UserFragment
import com.sbarifp.pemasaranproduk.utils.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBottomNav()
    }

    override fun onBackPressed() {
        val itemId = binding.btmNavMain.selectedItemId
        if(itemId == R.id.action_home){
            finish()
        }else{
            openHomeFragment()
        }
    }

    private fun initBottomNav() {
        binding.btmNavMain.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.action_home -> {
                    openFragment(HomeFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.action_sell -> {
                    startActivity<SellActivity>()
                }

                R.id.action_my_ads -> {
                    openFragment(MyAdsFragment())
                    return@setOnItemSelectedListener true
                }

                R.id.action_user -> {
                    openFragment(UserFragment())
                    return@setOnItemSelectedListener true
                }
            }
            return@setOnItemSelectedListener false
        }
        openHomeFragment()
    }

    private fun openHomeFragment() {
        binding.btmNavMain.selectedItemId = R.id.action_home
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, fragment)
            .addToBackStack(null)
            .commit()
    }
}