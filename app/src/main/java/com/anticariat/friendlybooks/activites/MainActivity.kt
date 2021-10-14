package com.anticariat.friendlybooks.activites

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ActivityMainBinding
import com.anticariat.friendlybooks.utils.Constants


class MainActivity :BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

            fullScreenWhitOutDownButton()

        val sharePreferences=getSharedPreferences(Constants.FB_PREFERENCES,Context.MODE_PRIVATE)
        val userName=sharePreferences.getString(Constants.LOGGED_IN_USERNAME,"")

        binding.tvHelloUser.text="Hello $userName"
    }
}