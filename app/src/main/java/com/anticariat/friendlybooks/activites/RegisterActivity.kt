package com.anticariat.friendlybooks.activites

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.graphics.drawable.DrawableCompat
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.firestore.FireStoreClass
import com.anticariat.friendlybooks.model.User
import com.anticariat.friendlybooks.utils.MSEditText
import com.anticariat.friendlybooks.utils.MSTextBold
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*


class RegisterActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // full screen
        fullScreenWhitOutDownButton()

        // go to LoginActivity
        tv_login.setOnClickListener() {
            onBackPressed()
        }
        btn_register.setOnClickListener { registerUser() }

        backToLoginActivity()

    }

    // Back button
    private fun backToLoginActivity() {
        iv_toolbar_back.setOnClickListener { onBackPressed() }
    }

    // Validation fun for new User fields
    private fun validationNewUserFields(): Boolean {

        return when {
            TextUtils.isEmpty(et_first_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_first_name), true)
                false
            }
            TextUtils.isEmpty(et_last_name.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_last_name), true)
                false
            }
            TextUtils.isEmpty(et_email_register.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_email), true)
                false
            }
            TextUtils.isEmpty(et_password_register.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_password), true)
                false
            }
            TextUtils.isEmpty(et_confirm_password_register.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_message_enter_confirm_password),
                    true
                )
                false
            }

            et_password_register.text.toString()
                .trim { it <= ' ' } != et_confirm_password_register.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_message_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !cb_agree_terms_and_condition.isChecked -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_message_agree_terms_and_condition),
                    true
                )
                false
            }
            else -> {
                // showErrorSnackBar(resources.getString(R.string.valid_details), false)
                true
            }
        }
    }

    private fun registerUser() {

        val email = et_email_register.text.toString().trim { it <= ' ' }
        val password = et_password_register.text.toString().trim { it <= ' ' }

        if (validationNewUserFields()) {
            showProgressBarDialog(resources.getString(R.string.please_wait))

            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val user= User(
                            firebaseUser.uid,
                            et_first_name.text.toString().trim { it <= ' ' },
                            et_last_name.text.toString().trim { it <= ' ' },
                            et_email_register.text.toString().trim { it <= ' ' })

                        FireStoreClass().userRegistration(this@RegisterActivity,user)
                        useRegistrationSuccess()

//                        FirebaseAuth.getInstance().signOut()
//                        finish()
                    } else {
                        hideProgressDialog()
                        // If sign in fails, display a message to the user.
                        showErrorSnackBar(task.exception!!.message.toString(), true)

                    }
                }
        }
    }

    internal fun useRegistrationSuccess() {

        hideProgressDialog()
        Toast.makeText(this@RegisterActivity,resources.getString(R.string.register_success),Toast.LENGTH_LONG).show()
        finish()

    }
}