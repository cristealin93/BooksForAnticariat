package com.anticariat.friendlybooks.activites

import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.core.content.ContextCompat
import com.anticariat.friendlybooks.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.progres_dialog.*

open class BaseActivity : AppCompatActivity() {

    lateinit var mProgressBar:Dialog

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
        mProgressBar= Dialog(this)

        mProgressBar.setContentView(R.layout.progres_dialog)
        mProgressBar.tv_loading_register.text=text

        mProgressBar.setCancelable(false)
        mProgressBar.setCanceledOnTouchOutside(false)

        mProgressBar.show()
    }

    fun hideProgressDialog(){
        mProgressBar.dismiss()
    }
}