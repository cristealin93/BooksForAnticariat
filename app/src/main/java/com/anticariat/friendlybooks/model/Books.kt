package com.anticariat.friendlybooks.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Books (
             var nameBooks:String?="",
             var infoBooks:String?="",
             var imgBooks:String?=""
):Parcelable
