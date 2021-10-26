package com.anticariat.friendlybooks.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.anticariat.friendlybooks.activites.LoginActivity
import com.anticariat.friendlybooks.activites.RegisterActivity
import com.anticariat.friendlybooks.activites.UserProfileActivity
import com.anticariat.friendlybooks.model.User
import com.anticariat.friendlybooks.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

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

    fun updateUserProfile(activity:Activity, userHashMap: HashMap<String, Any>){

        mFireStore.collection(Constants.USER).document(getCurrentUser()).update(userHashMap)
            .addOnSuccessListener {
                when(activity){
                    is UserProfileActivity->
                       activity.userProfileUpdateSuccess()
                }
            }
            .addOnFailureListener{e->
                when(activity){
                    is UserProfileActivity->
                        activity.hideProgressDialog()
                }

            }
    }

    fun uploadImageToCloudStorage(activity: Activity,imageFileUri: Uri?){

        val sRef:StorageReference=FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE+System.currentTimeMillis()+"."+
                    Constants.getFileExtension(activity,imageFileUri)
        )
        sRef.putFile(imageFileUri!!)
            .addOnSuccessListener { taskSnapshot->
            taskSnapshot.metadata!!.reference!!.downloadUrl
                .addOnSuccessListener { uri->
                    Log.e("Download image",uri.toString())

                    when(activity){
                        is UserProfileActivity-> {
                            activity.imageUploadSuccessfully(uri.toString())
                        }
                }
                }
        }
            .addOnFailureListener{exception->

                when(activity){
                    is UserProfileActivity->{
                        activity.hideProgressDialog()
                    }
                }
            }
    }
}