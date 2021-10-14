package com.anticariat.friendlybooks.activites

import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ProgresDialogBinding
import com.google.android.material.snackbar.Snackbar


open class BaseActivity : AppCompatActivity() {

    lateinit var mProgressBar:Dialog
    private lateinit var binding: ProgresDialogBinding

    fun showErrorSnackBar(message:String,errorMessage:Boolean){

        val snackBar=Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)

        val snackBarView=snackBar.view
        if(errorMessage){
            snackBarView.setBackgroundColor(ContextCompat.getColor(this@BaseActivity,R.color.colorSnackBarError))
        }else{
            snackBarView.setBackgroundColor(ContextCompat.getColor(this@BaseActivity,R.color.colorSnackBarSuccess))
        }
        snackBar.show()
    }


    fun fullScreenWhitOutDownButton(){
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
    }

    fun showProgressBarDialog(text:String){

        binding= ProgresDialogBinding.inflate(layoutInflater)

        mProgressBar= Dialog(this)

        mProgressBar.setContentView(binding.root)
        binding.tvLoadingRegister.text=text

        mProgressBar.setCancelable(false)
        mProgressBar.setCanceledOnTouchOutside(false)

        mProgressBar.show()
    }

    fun hideProgressDialog(){
        mProgressBar.dismiss()
    }


}