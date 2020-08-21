//* Created by Saira on 03/07/2019.
package com.customvideocalling.repositories

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

class RideRepository {
    var gson: Gson = GsonBuilder().serializeNulls().create() as Gson
     var data: MutableLiveData<RideListModel> = MutableLiveData()

    fun getaRideHistory(page: String): MutableLiveData<RideListModel> {
        if (UtilsFunctions.isNetworkConnected()) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(object : ApiResponse<JsonObject> {
                override fun onResponse(mResponse: Response<JsonObject>) {
                /*    val historyResponse = gson.fromJson<RideListModel>("" + mResponse.body()!!, RideListModel::class.java)

                    data.value = historyResponse*/

                }

                override fun onError(mKey: String) {
                    data.value = null
                   // UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                }

            }, ApiClient.getApiInterface().getRideHistory(page, "10")
                   )

        }
        return data
    }

}

