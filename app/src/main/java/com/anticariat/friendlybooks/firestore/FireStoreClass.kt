package com.anticariat.friendlybooks.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.anticariat.friendlybooks.activites.LoginActivity
import com.anticariat.friendlybooks.activites.RegisterActivity
import com.anticariat.friendlybooks.model.User
import com.anticariat.friendlybooks.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FireStoreClass {

    val mFireStore = FirebaseFirestore.getInstance()

    fun userRegistration(activity: RegisterActivity, userInfo: User) {

        mFireStore.collection(Constants.USER)
            .document(userInfo.id)
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {

                //Here call a function from base activity to transfer the result to it
                activity.useRegistrationSuccess()
            }.addOnFailureListener {
                activity.hideProgressDialog()

            }
    }

    fun getCurrentUser():String{
        val currentUserID=FirebaseAuth.getInstance().currentUser
        var currentUser=""
        if(currentUserID!=null){
            currentUser=currentUserID.uid
        }
        return currentUser
    }

    fun getUserDetails(activity: Activity){

        mFireStore.collection(Constants.USER)
            .document(getCurrentUser())
            .get()
            .addOnSuccessListener { document->

                val user=document.toObject(User::class.java)!!

                val sharePreferences=activity.getSharedPreferences(Constants.FB_PREFERENCES,Context.MODE_PRIVATE)

                val editor:SharedPreferences.Editor=sharePreferences.edit()
                editor.putString(Constants.LOGGED_IN_USERNAME,"${user.firstName} ${user.lastName}")

                editor.apply()

                when(activity){
                    is LoginActivity->{
                        activity.userLoggedInSuccess(user)
                    }
                }
            }.addOnFailureListener {

                when(activity){
                    is LoginActivity->{
                        activity.hideProgressDialog()
                    }
                }



            }
    }
}