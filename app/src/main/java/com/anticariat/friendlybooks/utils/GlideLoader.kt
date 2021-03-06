package com.anticariat.friendlybooks.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.anticariat.friendlybooks.R
import com.bumptech.glide.Glide
import java.io.IOException

class GlideLoader(val context: Context) {
    fun loaderUserPicture(image: Any, imageView:ImageView){
        try{
            Glide
                .with(context)
                .load(image)
                .centerCrop()
                .placeholder(R.drawable.ic_baseline_supervised_user_circle_24)
                .into(imageView)
        }catch (e: IOException){
            e.printStackTrace()

        }
    }
}