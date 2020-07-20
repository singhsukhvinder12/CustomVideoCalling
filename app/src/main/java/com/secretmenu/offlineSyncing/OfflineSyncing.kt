package com.secretmenu.offlineSyncing

import android.os.AsyncTask
import com.google.gson.JsonObject
import com.secretmenu.application.MyApplication
import com.secretmenu.common.UtilsFunctions
import com.secretmenu.repositories.UserRepository
import com.secretmenu.roomdatabase.User

object OfflineSyncing {
    @JvmStatic
    fun userDataSyncing() {
        var userOfflineList = listOf<User>()
        val userRepository = UserRepository()
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
                userOfflineList = MyApplication.appDatabase.userDao().findOffline()

                for (i in 0 until userOfflineList.size) {
                    val mJsonObject = JsonObject()
                    mJsonObject.addProperty("first_name", userOfflineList[i].firstName)
                    mJsonObject.addProperty("last_name", userOfflineList[i].lastName)
                    if(UtilsFunctions.isNetworkConnectedWithout())userRepository.uploadUserEntry(userOfflineList[i].uid, mJsonObject)

                }
                return null
            }

        }.execute()

    }

}
