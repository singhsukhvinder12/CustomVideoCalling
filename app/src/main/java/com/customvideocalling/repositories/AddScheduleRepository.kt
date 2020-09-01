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

class AddScheduleRepository {
    private var data : MutableLiveData<ScheduleListResponse>? = null
    private var data1 : MutableLiveData<CommonModel>? = null
    private var dataScheduleDetail : MutableLiveData<ScheduleDetailResponse>? = null



    init {
        data = MutableLiveData()
        data1 = MutableLiveData()
        dataScheduleDetail = MutableLiveData()

    }

    fun getScheduleList(callBack: CallBackResult.ScheduleListCallBack, userId: String): MutableLiveData<ScheduleListResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getScheduleList(userId)
        call.enqueue(object : Callback<ScheduleListResponse> {
            override fun onResponse(
                call: Call<ScheduleListResponse>,
                response: retrofit2.Response<ScheduleListResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onScheduleListSuccess(response.body()!!)
                    }
                    else {
                        callBack.onScheduleListFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onScheduleListFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<ScheduleListResponse>, t: Throwable) {
                callBack.onScheduleListFailed(t.message!!)
            }
        })
        return data!!
    }


    fun hitAddSchedule(callBack: CallBackResult.AddScheduleCallBack, jsonObject:JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).hitAddScheduleApi(jsonObject)
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: retrofit2.Response<CommonModel>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onAddScheduleSuccess(response.body()!!)
                    }
                    else {
                        callBack.onAddScheduleFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onAddScheduleFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                callBack.onAddScheduleFailed(t.message!!)
            }
        })
        return data1!!
    }

    fun scheduleDetailApi(callBack: CallBackResult.ScheduleDetailCallBack, jsonObject:JsonObject): MutableLiveData<ScheduleDetailResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getScheduleDetail(jsonObject)
        call.enqueue(object : Callback<ScheduleDetailResponse> {
            override fun onResponse(
                call: Call<ScheduleDetailResponse>,
                response: retrofit2.Response<ScheduleDetailResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onScheduleDetailSuccess(response.body()!!)
                    }
                    else {
                        callBack.onScheduleDetailFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onScheduleDetailFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<ScheduleDetailResponse>, t: Throwable) {
                callBack.onScheduleDetailFailed(t.message!!)
            }
        })
        return dataScheduleDetail!!
    }

}