package com.anticariat.friendlybooks.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.TextView
import com.anticariat.friendlybooks.R


class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_acrivity)

        // Hide ActionBar and NavBar
        // full screen
        fullScreenWhitOutDownButton()

//        val type:Typeface=Typeface.createFromAsset(assets,"Montserrat-Bold.ttf")
//            txt_splash.typeface=type

        // Delay to go to the next activity
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        }, 2500)

    }
}