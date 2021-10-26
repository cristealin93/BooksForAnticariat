package com.anticariat.friendlybooks.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {


    const val USER:String="user"
    const val FB_PREFERENCES:String="FriendlyBooksPref"
    const val LOGGED_IN_USERNAME:String="logged_in_username"
    const val EXTRA_USER_DETAILS:String="extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE: Int=2

    const val MALE:String="male"
    const val FEMALE:String="female"

    const val MOBILE:String="mobile"
    const val GENDER:String="gender"

    const val USER_PROFILE_IMAGE:String="user_profile_image"

    fun getFileExtension(activity: Activity,uri: Uri?):String?
    {

        return MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(activity.contentResolver.getType(uri!!))
    }
}