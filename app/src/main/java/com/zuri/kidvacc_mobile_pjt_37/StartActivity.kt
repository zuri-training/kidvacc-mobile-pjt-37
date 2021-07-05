package com.zuri.kidvacc_mobile_pjt_37

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_signup)

        val btnSignUP: Button = findViewById(R.id.btn_sign_up)
        btnSignUP.setOnClickListener { setContentView(R.layout.fragment_login) }
    }
}