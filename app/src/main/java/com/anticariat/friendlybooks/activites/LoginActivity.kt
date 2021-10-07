package com.anticariat.friendlybooks.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.utils.MSTextBold

class LoginActivity : AppCompatActivity() {

    private lateinit var tv_register:MSTextBold

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        tv_register=findViewById(R.id.tv_register)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        tv_register.setOnClickListener(){

            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
            finish()
        }

    }
}