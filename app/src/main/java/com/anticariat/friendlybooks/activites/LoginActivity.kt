package com.anticariat.friendlybooks.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ActivityLoginBinding
import com.anticariat.friendlybooks.firestore.FireStoreClass
import com.anticariat.friendlybooks.model.User
import com.anticariat.friendlybooks.utils.Constants
import com.anticariat.friendlybooks.utils.MSTextBold
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // full screen
        fullScreenWhitOutDownButton()
        binding.btnLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvForgotPassword.setOnClickListener(this)
    }

    fun userLoggedInSuccess(user: User) {

        hideProgressDialog()


        if(user.profileCompleted==0){
            val intent=Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS,user)
            startActivity(intent)
        }else{
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }


        finish()
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.tv_forgot_password -> {
                    startActivity(Intent(this@LoginActivity, ForgotPasswordActivity::class.java))
                }
                R.id.btn_login -> {
                    logInRegistedUser()
                }
                R.id.tv_register -> {
                    startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))

                }
            }
        }
    }

    private fun logInRegistedUser() {
        if (validationLogInDetails()) {
            showProgressBarDialog(resources.getString(R.string.please_wait))

            val email = binding.etEmail.text.toString().trim { it <= ' ' }
            val password = binding.etPassword.text.toString().trim { it <= ' ' }

            // LogIn with Firebase
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        FireStoreClass().getUserDetails(this@LoginActivity)
                    } else {
                        hideProgressDialog()
                        showErrorSnackBar(task.exception!!.toString(), true)
                    }
                }
        }
    }

    private fun validationLogInDetails(): Boolean {

        return when {
            TextUtils.isEmpty(binding.etEmail.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_password), true)
                false
            }
            else -> {
                // showErrorSnackBar("Your details are valid.", false)
                true
            }
        }
    }


}