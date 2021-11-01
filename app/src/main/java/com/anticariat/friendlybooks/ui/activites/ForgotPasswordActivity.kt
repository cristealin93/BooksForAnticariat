package com.anticariat.friendlybooks.ui.activites

import android.os.Bundle
import android.widget.Toast
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ActivityForgotPasswordActicityBinding
import com.google.firebase.auth.FirebaseAuth


class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding: ActivityForgotPasswordActicityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityForgotPasswordActicityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fullScreenWhitOutDownButton()
        binding.ivToolbarBackFP.setOnClickListener { onBackPressed() }

        binding.btnSubmit.setOnClickListener{
            val email=binding.etEmailFP.text.toString().trim { it<=' ' }
            if(email.isEmpty()){
                showErrorSnackBar(resources.getString(R.string.err_message_enter_email,),true)
            }else{
                showProgressBarDialog(resources.getString(R.string.please_wait))
                sendResetEmail(email)
            }
        }
    }

    private fun sendResetEmail(email: String) {

        FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener{task->
            hideProgressDialog()
            if(task.isSuccessful){
                Toast.makeText(this@ForgotPasswordActivity,resources.getString(R.string.check_email),Toast.LENGTH_LONG).show()
                finish()
            }else{
                showErrorSnackBar(task.exception?.message.toString(),true)
            }
        }
    }
}