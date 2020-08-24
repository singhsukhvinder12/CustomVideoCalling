package com.customvideocalling.repositories

import androidx.lifecycle.MutableLiveData
import com.customvideocalling.Interfaces.CallBackResult
import com.example.artha.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.customvideocalling.R
import com.customvideocalling.api.ApiClient
import com.customvideocalling.api.ApiResponse
import com.customvideocalling.api.ApiService
import com.customvideocalling.api.GetRestAdapter
import com.customvideocalling.application.MyApplication
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherNotificationRepository {
    private var data : MutableLiveData<TeacherNotificationListResponse>? = null
    private var data1 : MutableLiveData<CommonModel>? = null



    init {
        data = MutableLiveData()
        data1 = MutableLiveData()

    }

    fun getTeacherNotificationList(callBack: CallBackResult.TeacherNotificationListCallBack, userId: String): MutableLiveData<TeacherNotificationListResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getTeacherNotificationList(userId)
        call.enqueue(object : Callback<TeacherNotificationListResponse> {
            override fun onResponse(
                call: Call<TeacherNotificationListResponse>,
                response: retrofit2.Response<TeacherNotificationListResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onTeacherNotificationListSuccess(response.body()!!)
                    }
                    else {
                        callBack.oTeacherNotificationListFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.oTeacherNotificationListFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<TeacherNotificationListResponse>, t: Throwable) {
                callBack.oTeacherNotificationListFailed(t.message!!)
            }
        })
        return data!!
    }


    fun hitAcceptRejectApi(callBack: CallBackResult.TeacherAcceptRejectApiCallBack, jsonObject:JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).hitAcceptRejectApi(jsonObject)
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: retrofit2.Response<CommonModel>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onTeacherAcceptRejectSuccess(response.body()!!)
                    }
                    else {
                        callBack.onTeacherAcceptRejectFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onTeacherAcceptRejectFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                callBack.onTeacherAcceptRejectFailed(t.message!!)
            }
        })
        return data1!!
    }

}