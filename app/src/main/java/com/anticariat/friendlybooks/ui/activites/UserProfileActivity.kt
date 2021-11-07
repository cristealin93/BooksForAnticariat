package com.anticariat.friendlybooks.ui.activites

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ActivityUserProfileBinding
import com.anticariat.friendlybooks.firestore.FireStoreClass
import com.anticariat.friendlybooks.model.User
import com.anticariat.friendlybooks.utils.Constants
import com.anticariat.friendlybooks.utils.GlideLoader
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {

    private lateinit var userDetails: User
    private var mSelectedImageFileUri: Uri? = null
    private var mUserProfileImageURL: String = ""

    lateinit var getContent: ActivityResultLauncher<String>

    private lateinit var binding: ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
            try {
                mSelectedImageFileUri = it
                GlideLoader(this).loaderUserPicture(mSelectedImageFileUri!!, binding.ivUserPhoto)
            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(
                    this@UserProfileActivity,
                    "Image selection failed",
                    Toast.LENGTH_SHORT
                ).show()
            }


        }
        userDetails = User()
        if (intent.hasExtra(Constants.EXTRA_USER_DETAILS)) {

            userDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }

        binding.etLastName.setText(userDetails.lastName)
        binding.etFirstName.setText(userDetails.firstName)
        binding.etEmail.isEnabled = false
        binding.etEmail.setText(userDetails.email)

        if(userDetails.profileCompleted==0){

            binding.etFirstName.isEnabled = false
            binding.etLastName.isEnabled = false
        }
        else{
            binding.etFirstName.isEnabled = true
            binding.etLastName.isEnabled = true
            GlideLoader(this).loaderUserPicture(userDetails.image, binding.ivUserPhoto)

            if(userDetails.mobile!=0L){
                binding.etPhoneNumber.setText(userDetails.mobile.toString())
            }

            if(userDetails.gender==Constants.MALE){
                binding.rbMale.isChecked=true
            }else{
                binding.rbFemale.isChecked=true
            }
        }


        binding.ivUserPhoto.setOnClickListener(this@UserProfileActivity)
        binding.btnSubmit.setOnClickListener(this@UserProfileActivity)

    }

    override fun onClick(v: View?) {
        if (v != null) {
            when (v.id) {
                R.id.iv_user_photo -> {

                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        setImageForResult()

                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE
                        )
                    }
                }
                R.id.btn_submit -> {

                    if (checkEmailAndGender()) {
                        showProgressBarDialog(resources.getString(R.string.please_wait))
                        if(mSelectedImageFileUri!=null) {
                            FireStoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)
                        }else{
                            updateUserProfileDetails()
                        }

                    }
                }
            }

        }
    }

    private fun updateUserProfileDetails() {
        val userHashMap = HashMap<String, Any>()

        val firstName=binding.etFirstName.text.toString().trim{it<=' '}
        val lastName=binding.etLastName.text.toString().trim{it<=' '}

        if(firstName!=userDetails.firstName){
            userHashMap[Constants.FIRST_NAME] = firstName
        }
        if(lastName!=userDetails.lastName){
            userHashMap[Constants.LAST_NAME] = lastName
        }

        val userPhoneNumber = binding.etPhoneNumber.text.toString().trim { it <= ' ' }
        val gender = if (binding.rbMale.isChecked) {
            Constants.MALE
        } else {
            Constants.FEMALE
        }
        if (userPhoneNumber.isNotEmpty() && userPhoneNumber!=userDetails.mobile.toString()) {
            userHashMap[Constants.MOBILE] = userPhoneNumber.toLong()
        }
        if (gender.isNotEmpty() && gender!=userDetails.gender) {
            userHashMap[Constants.GENDER] = gender
        }
        if(mUserProfileImageURL.isNotEmpty()){
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
        }
        userHashMap[Constants.COMPLETE_PROFILE]=1
        FireStoreClass().updateUserProfile(this, userHashMap)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setImageForResult()

            } else {
                Toast.makeText(
                    this,
                    resources.getString(R.string.read_storage_permission_denited),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setImageForResult() {
        getContent.launch("image/*")
    }

    private fun checkEmailAndGender(): Boolean {

        return when {

            TextUtils.isEmpty(binding.etPhoneNumber.text.toString().trim { it <= ' ' }) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_moblie_number), true)
                false
            }
            else -> {
                true
            }

        }

    }

    internal fun userProfileUpdateSuccess() {
        hideProgressDialog()

        Toast.makeText(
            this@UserProfileActivity,
            "Your profile was updated successfully.",
            Toast.LENGTH_LONG
        ).show()

        startActivity(Intent(this@UserProfileActivity, DashBoardActivity::class.java))
        finish()
    }

    fun imageUploadSuccessfully(imageURL: String) {

        //hideProgressDialog()
        mUserProfileImageURL = imageURL
        updateUserProfileDetails()
    }
}