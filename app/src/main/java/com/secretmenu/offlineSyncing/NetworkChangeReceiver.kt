package com.secretmenu.offlineSyncing

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import com.secretmenu.common.UtilsFunctions

class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            if (isOnline(context)) {
                UtilsFunctions.showToastSuccess("App is Online")
                OfflineSyncing.userDataSyncing()
            } else {
                UtilsFunctions.showToastSuccess("App is Offline")
            }
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }

    }

    private fun isOnline(context: Context): Boolean {
        try {
            val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            //should check null because in airplane mode it will be null
            return netInfo != null && netInfo.isConnected
        } catch (e: NullPointerException) {
            e.printStackTrace()
            return false
        }

    }
}