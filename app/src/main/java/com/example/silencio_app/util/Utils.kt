package com.example.silencio_app.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.google.android.material.snackbar.Snackbar
import timber.log.Timber
import java.util.*

object Utils {

    fun snackBar(view: View?, context: Context?, message: String?) {
        if (message != null && view != null) {
            val imm =
                context!!.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            val snackbar =
                Snackbar.make(view, message, Snackbar.LENGTH_LONG)
            val snackbarView = snackbar.view
            val snackTextView =
                snackbarView.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView

            snackTextView.maxLines = 4
            snackbar.show()
        }
    }


//    fun checkForFCM(context: Context?, callback: (oldToken: String, newToken: String) -> Unit) {
//        // to get FCM token
//        try {
//            if (checkPlayServices(context as Activity)) {
//               addOnCompleteListener(OnCompleteListener { task ->
//                    if (!task.isSuccessful) {
//                        Timber.e("Fetching FCM registration token failed", task.exception)
//                        return@OnCompleteListener
//                    }
//                    val token = task.result
//                    Timber.d("fcm token===" + token.toString())
//                    val oldToken = PrefManagers.getString(PrefManagers.FIRE_BASE_TOKEN)
//                    if (oldToken != token) {
//                        // token renewed
//                        callback(oldToken, token)
//                    } else {
//                        callback(token, token)
//                    }
//                    PrefManagers.setString(PrefManagers.FIRE_BASE_TOKEN, token)
//                })
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    private fun checkPlayServices(activity: Activity): Boolean {
       // val apiAvailability = GoogleApiAvailability.getInstance()
       // val resultCode =
           // GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity)
        if (true) {
            } else {
                Timber.d(
                    "==checkPlayServices==" +
                            "This device is not supported."
                )
                activity.finish()
            }
            return false
        }



    fun logout(context: Context) {
        try {

            PrefManagers.clearAll()
            val intent : Intent?=
             context.packageManager.getLaunchIntentForPackage(context.packageName)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            ContextCompat.startActivity(context,intent!!,null)

        } catch (e: java.lang.Exception) {
            e.printStackTrace()
            Timber.e(e.localizedMessage)
        }
    }

    fun timeAgo(
        date: Long
    ): String {
        try {
            val currentTime = Date().time
            val delta = currentTime - date
            val elapsedHour = delta / (1000 * 60 * 60)

            Timber.e("$elapsedHour === ${date} === $currentTime")
            return "$elapsedHour h ago"

        } catch (e: Exception){
            e.printStackTrace()
            return ""
        }
    }

    fun share(text:String,context: Context){
        val intent= Intent()
        intent.action=Intent.ACTION_SEND
        intent.putExtra(Intent.EXTRA_TEXT,"Hey Check out this Great app:")
        intent.type="text/plain"
        also {
             context.startActivity(Intent.createChooser(intent,"Share To:"))
        }
    }


}