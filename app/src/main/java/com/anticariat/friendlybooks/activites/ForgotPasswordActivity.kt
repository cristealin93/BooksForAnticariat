package com.anticariat.friendlybooks.activites

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.anticariat.friendlybooks.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_forgot_password_acticity.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.iv_toolbar_back

class ForgotPasswordActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password_acticity)

        fullScreenWhitOutDownButton()
        iv_toolbar_back_FP.setOnClickListener { onBackPressed() }

        btn_submit.setOnClickListener{
            val email=et_email_FP.text.toString().trim { it<=' ' }
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