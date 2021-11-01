package com.anticariat.friendlybooks.ui.activites

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ActivityRegisterBinding
import com.anticariat.friendlybooks.firestore.FireStoreClass
import com.anticariat.friendlybooks.model.User
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class RegisterActivity : BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterBinding.inflate(layoutInflater)

        setContentView(binding.root)

        // full screen
        fullScreenWhitOutDownButton()

        // go to LoginActivity
        binding.tvLogin.setOnClickListener() {
            onBackPressed()
        }
        binding.btnRegister.setOnClickListener { registerUser() }

        backToLoginActivity()

    }

    // Back button
    private fun backToLoginActivity() {
        binding.ivToolbarBack.setOnClickListener { onBackPressed() }
    }

    // Validation fun for new User fields
    private fun validationNewUserFields(): Boolean {

        return when {
            TextUtils.isEmpty(binding.etFirstName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_first_name), true)
                false
            }
            TextUtils.isEmpty(binding.etLastName.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_last_name), true)
                false
            }
            TextUtils.isEmpty(binding.etEmailRegister.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPasswordRegister.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_message_enter_password), true)
                false
            }
            TextUtils.isEmpty(binding.etConfirmPasswordRegister.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_message_enter_confirm_password),
                    true
                )
                false
            }

            binding.etPasswordRegister.text.toString()
                .trim { it <= ' ' } != binding.etConfirmPasswordRegister.text.toString()
                .trim { it <= ' ' } -> {
                showErrorSnackBar(
                    resources.getString(R.string.err_message_password_and_confirm_password_mismatch),
                    true
                )
                false
            }
            !binding.cbAgreeTermsAndCondition.isChecked -> {
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

        val email = binding.etEmailRegister.text.toString().trim { it <= ' ' }
        val password = binding.etPasswordRegister.text.toString().trim { it <= ' ' }

        if (validationNewUserFields()) {
            showProgressBarDialog(resources.getString(R.string.please_wait))

            Firebase.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->

                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        val firebaseUser: FirebaseUser = task.result!!.user!!

                        val user= User(
                            firebaseUser.uid,
                            binding.etFirstName.text.toString().trim { it <= ' ' },
                            binding.etLastName.text.toString().trim { it <= ' ' },
                            binding.etEmailRegister.text.toString().trim { it <= ' ' })

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