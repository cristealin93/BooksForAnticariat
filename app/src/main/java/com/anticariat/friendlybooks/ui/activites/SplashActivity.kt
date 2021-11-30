package com.anticariat.friendlybooks.ui.activites

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.anticariat.friendlybooks.R
import com.google.firebase.auth.FirebaseAuth


class SplashActivity : BaseActivity() {

    private lateinit var mAuthFirebase: FirebaseAuth

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
            mAuthFirebase=FirebaseAuth.getInstance()
            if(mAuthFirebase.currentUser!=null){
                startActivity(Intent(this@SplashActivity, DashBoardActivity::class.java))
            }else{
                startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                finish()
            }
        }, 2000)


    }
}