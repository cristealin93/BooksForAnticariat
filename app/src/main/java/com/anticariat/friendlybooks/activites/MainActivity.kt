package com.anticariat.friendlybooks.activites

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.utils.Constants
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity :BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

            fullScreenWhitOutDownButton()

        val sharePreferences=getSharedPreferences(Constants.FB_PREFERENCES,Context.MODE_PRIVATE)
        val userName=sharePreferences.getString(Constants.LOGGED_IN_USERNAME,"")

        tv_hello_user.text="Hello $userName"
    }
}