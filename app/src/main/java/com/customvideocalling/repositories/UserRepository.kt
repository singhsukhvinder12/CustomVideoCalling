//* Created by Saira on 03/07/2019.
package com.customvideocalling.repositories

import android.annotation.SuppressLint
import android.os.AsyncTask
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.customvideocalling.R
import com.customvideocalling.api.ApiClient
import com.customvideocalling.api.ApiResponse
import com.customvideocalling.api.ApiService
import com.customvideocalling.application.MyApplication
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.RideListModel
import retrofit2.Response
@SuppressLint("StaticFieldLeak")
class UserRepository {
    var gson: Gson = GsonBuilder().serializeNulls().create() as Gson
    var data: MutableLiveData<RideListModel> = MutableLiveData()

    fun uploadUserEntry(uid: Int, args: JsonObject): MutableLiveData<RideListModel> {
        if (UtilsFunctions.isNetworkConnected()) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(object : ApiResponse<JsonObject> {
                override fun onResponse(mResponse: Response<JsonObject>) {
                    val response = gson.fromJson<RideListModel>("" + mResponse.body()!!, RideListModel::class.java)

                    data.value = response
                    if (response.code == 200 || response.code==404) {
                        object : AsyncTask<Void, Void, Void>() {
                            override fun doInBackground(vararg voids: Void): Void? {
                               // MyApplication.appDatabase.userDao().updateStatus(1,uid)
                                return null
                            }
                        }.execute()
                    }
                }

                override fun onError(mKey: String) {
                    data.value = null
               //     UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                }

            }, ApiClient.getApiInterface().getRideHistory("1","10")
                    )

        }
        return data
    }

}

