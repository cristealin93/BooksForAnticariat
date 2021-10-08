package com.anticariat.friendlybooks.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.utils.MSTextBold
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // full screen
        fullScreenWhitOutDownButton()

        tv_register.setOnClickListener(){

            startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))

        }

    }
}