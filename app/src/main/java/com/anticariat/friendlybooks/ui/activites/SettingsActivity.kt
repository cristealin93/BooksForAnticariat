package com.anticariat.friendlybooks.ui.activites

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ActivityRegisterBinding
import com.anticariat.friendlybooks.databinding.ActivitySettingsBinding
import com.anticariat.friendlybooks.firestore.FireStoreClass
import com.anticariat.friendlybooks.model.User
import com.anticariat.friendlybooks.utils.Constants
import com.anticariat.friendlybooks.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import java.io.IOException

class SettingsActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var mUserDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        fullScreenWhitOutDownButton()
        setUpActionBar()

        binding.btnLogout.setOnClickListener(this)
        binding.tvEdit.setOnClickListener(this)
    }

    private fun setUpActionBar() {
        setSupportActionBar(binding.tbSettingsActivity)
        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_back_24)
        }

        binding.tbSettingsActivity.setNavigationOnClickListener { onBackPressed() }

    }

    private fun getUserDetails() {
        showProgressBarDialog(resources.getString(R.string.please_wait))
        FireStoreClass().getUserDetails(this)
    }

    @SuppressLint("SetTextI18n")
    fun userDetailsSuccessfully(user: User) {
        mUserDetails=user
        hideProgressDialog()
        GlideLoader(this).loaderUserPicture(user.image, binding.ivUserPhoto)

        binding.tvName.text = "${user.firstName} ${user.lastName}"
        binding.tvGender.text = user.gender
        binding.tvEmail.text = user.email
        binding.tvPhoneNumber.text = user.mobile.toString()


    }

    override fun onResume() {
        super.onResume()
        getUserDetails()
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {

                R.id.tv_edit->{

                    val intent=Intent(this@SettingsActivity, UserProfileActivity::class.java)
                    intent.putExtra(Constants.EXTRA_USER_DETAILS,mUserDetails)
                    startActivity(intent)
                    finish()
                }

                R.id.btn_logout->{

                    FirebaseAuth.getInstance().signOut()

                    val intent= Intent(this@SettingsActivity,LoginActivity::class.java)
                    intent.flags=Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }


            }
        }
    }
}