package com.anticariat.friendlybooks.ui.activites

import android.app.Dialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.os.Looper
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.anticariat.friendlybooks.R
import com.anticariat.friendlybooks.databinding.ProgresDialogBinding
import com.google.android.material.snackbar.Snackbar


open class BaseActivity : AppCompatActivity() {

    private lateinit var mProgressBar:Dialog
    private lateinit var binding: ProgresDialogBinding
    private var mDoublePress:Boolean=false

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

    fun pressDoubleForExit(){
        if(mDoublePress){
        super.onBackPressed()
            return
        }

        this.mDoublePress=true
        Toast.makeText(this,"Press again to exit.",Toast.LENGTH_SHORT).show()

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({ mDoublePress=false
        }, 2000)


    }

}