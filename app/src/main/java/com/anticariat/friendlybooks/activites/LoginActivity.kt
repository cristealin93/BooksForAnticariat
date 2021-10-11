package com.anticariat.friendlybooks.activites

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.utils.MSTextBold
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*

class LoginActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // full screen
        fullScreenWhitOutDownButton()
        btn_login.setOnClickListener(this)
        tv_register.setOnClickListener(this)
        tv_forgot_password.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        if (view != null) {
            when (view.id) {
                R.id.tv_forgot_password -> {
                    startActivity(Intent(this@LoginActivity,ForgotPasswordActivity::class.java))
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
        if(validationLogInDetails()){
            showProgressBarDialog(resources.getString(R.string.please_wait))

            val email=et_email.text.toString().trim{it<=' '}
            val password=et_password.text.toString().trim{it<=' '}

            // LogIn with Firebase

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email,password)
                .addOnCompleteListener{task->
                    hideProgressDialog()
                    if (task.isSuccessful){
                        showErrorSnackBar("Login successfully!",false)
                    }else{
                        showErrorSnackBar(task.exception!!.toString(), true)
                    }
                }
        }
    }

    private fun validationLogInDetails(): Boolean {

        return when {
            TextUtils.isEmpty(et_email.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password.text.toString().trim { it <= ' ' }) -> {
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