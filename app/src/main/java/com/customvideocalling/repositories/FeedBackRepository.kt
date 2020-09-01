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
import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.model.SlotListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FeedBackRepository {
    private var dataFeedBack : MutableLiveData<CommonModel>? = null


    init {
        dataFeedBack = MutableLiveData()
    }

    fun addFeedback(callBack: CallBackResult.AddBookingCallBack,  mJsonObject: JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).addFeedback(mJsonObject)
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: retrofit2.Response<CommonModel>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onAddBookingSuccess(response.body()!!)
                    }
                    else {
                        callBack.onAddBookingFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onAddBookingFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                callBack.onAddBookingFailed(t.message!!)
            }
        })
        return dataFeedBack!!
    }

}