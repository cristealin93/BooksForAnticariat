package com.anticariat.friendlybooks.utils

import android.app.Activity
import android.content.ContentResolver
import android.net.Uri
import android.webkit.MimeTypeMap


object Constants {


    const val USER: String = "user"
    const val FB_PREFERENCES: String = "FriendlyBooksPref"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val READ_STORAGE_PERMISSION_CODE: Int = 2

    const val MALE: String = "male"
    const val FEMALE: String = "female"
    const val FIRST_NAME: String = "firstName"
    const val LAST_NAME: String = "lastName"

    const val MOBILE: String = "mobile"
    const val GENDER: String = "gender"
    const val IMAGE: String = "image"
    const val COMPLETE_PROFILE: String = "profileCompleted"


    const val USER_PROFILE_IMAGE: String = "user_profile_image"

    fun getFileExtension(activity: Activity, uri: Uri?): String? {

        val cR: ContentResolver = activity.contentResolver
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(cR.getType(uri!!))
    }
}