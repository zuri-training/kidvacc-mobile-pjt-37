package com.zuri.kidvacc_mobile_pjt_37

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        /*val sharedPref = this.getSharedPreferences("com.zuri.kidvacc_mobile_pjt_37",Context.MODE_PRIVATE) ?: return
        val exit = sharedPref.getBoolean("Exit", false)
        if (exit){
            with (sharedPref.edit()) {
                putBoolean("Exit", false)
                apply()
                onBackPressed()
            }
        }*/
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}