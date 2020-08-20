package com.customvideocalling.common

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.customvideocalling.R
import com.customvideocalling.application.MyApplication

object UtilsFunctions {
    @JvmStatic
    fun showToastError(message: String) {
        val toast = Toast.makeText(MyApplication.instance, message, LENGTH_LONG) as Toast
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 0)
        val view = toast.view
        val group = toast.view as ViewGroup
        val messageTextView = group.getChildAt(0) as TextView
        messageTextView.textSize = 15.0f
        messageTextView.gravity = Gravity.CENTER
        view!!.setBackgroundColor(ContextCompat.getColor(MyApplication.instance, R.color.colorRed))
        toast.show()
    }

    @JvmStatic
    @TargetApi(Build.VERSION_CODES.M)
    fun isNetworkConnectedWithoutToast() : Boolean {
        val cm = MyApplication.instance.applicationContext
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        var activeNetwork : NetworkInfo? = null
        activeNetwork = cm.activeNetworkInfo

        return if (activeNetwork != null && activeNetwork.isConnectedOrConnecting) {
            activeNetwork != null && activeNetwork.isConnectedOrConnecting
        } else {
            // showToastWarning(MyApplication.instance.getString(R.string.internet_connection))
            // Toast.makeText(MyApplication.getInstance().getApplicationContext(), R.string.internet_connection, Toast.LENGTH_SHORT).show();
            false
        }
    }

    @JvmStatic
    fun checkObjectNull(obj : Any?) : Boolean {
        return obj != null
    }

    @JvmStatic
    fun showToastSuccess(message: String) {
        val toast = Toast.makeText(MyApplication.instance, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.FILL_HORIZONTAL or Gravity.BOTTOM, 0, 0)
        val view = toast.view
        val group = toast.view as ViewGroup
        val messageTextView = group.getChildAt(0) as TextView
        messageTextView.textSize = 15.0f
        messageTextView.gravity = Gravity.CENTER
        view!!.setBackgroundColor(ContextCompat.getColor(MyApplication.instance, R.color.colorSuccess))
        toast.show()

    }

    @JvmStatic
    fun showToastWarning(message: String?) {
        val toast = Toast.makeText(MyApplication.instance, message, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.BOTTOM or Gravity.FILL_HORIZONTAL, 0, 0)
        val view = toast.view
        val group = toast.view as ViewGroup
        val messageTextView = group.getChildAt(0) as TextView
        messageTextView.textSize = 16.0f
        messageTextView.gravity = Gravity.CENTER
        view!!.setBackgroundColor(ContextCompat.getColor(MyApplication.instance, R.color.transparentBlack))
        toast.show()
    }

    fun changeThemeMode(mode: Int) {
        if (mode == 0)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

    }


    @SuppressLint("HardwareIds")
    @JvmStatic
    fun getAndroidID() : String {
        return Settings.Secure.getString(
                MyApplication.instance.contentResolver,
                Settings.Secure.ANDROID_ID
        )

    }
    fun isNetworkConnected(): Boolean {
        val cm = MyApplication.instance.applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo?
        activeNetwork = cm.activeNetworkInfo

        return if (activeNetwork != null && activeNetwork.isConnected) {
            activeNetwork.isConnected
            true
        } else {
            showToastWarning(MyApplication.instance.getString(R.string.internet_connection))
            // Toast.makeText(MyApplication.getInstance().getApplicationContext(), R.string.internet_connection, Toast.LENGTH_SHORT).show();
            false
        }
    }

    fun isNetworkConnectedWithout(): Boolean {
        val cm = MyApplication.instance.applicationContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo?
        activeNetwork = cm.activeNetworkInfo

        return if (activeNetwork != null && activeNetwork.isConnected) {
            activeNetwork.isConnected
            true
        } else {
            // Toast.makeText(MyApplication.getInstance().getApplicationContext(), R.string.internet_connection, Toast.LENGTH_SHORT).show();
            false
        }
    }



    @JvmStatic
    fun hideKeyBoard(view : View) {
        val imm = MyApplication.instance
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
