package com.anticariat.friendlybooks.ui.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.anticariat.friendlybooks.R

class SettingsActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        fullScreenWhitOutDownButton()
    }
}