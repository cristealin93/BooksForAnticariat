package com.anticariat.friendlybooks.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.utils.MSTextBold

class RegisterActivity : AppCompatActivity() {

    private lateinit var tv_login: MSTextBold

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        tv_login=findViewById(R.id.tv_login)

        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }

        tv_login.setOnClickListener(){

            startActivity(Intent(this@RegisterActivity,LoginActivity::class.java))
            finish()
        }
    }
}