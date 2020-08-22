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

class TokenHistoryRepository {
    private var data : MutableLiveData<TokenHistoryListResponse>? = null



    init {
        data = MutableLiveData()

    }

    fun getTokenHistoryList(callBack: CallBackResult.TokenHistoryCallBack, userId: String): MutableLiveData<TokenHistoryListResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getTokenHistoryList(userId)
        call.enqueue(object : Callback<TokenHistoryListResponse> {
            override fun onResponse(
                call: Call<TokenHistoryListResponse>,
                response: retrofit2.Response<TokenHistoryListResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onGetTokenHistorySuccess(response.body()!!)
                    }
                    else {
                        callBack.onGetTokenHistoryFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onGetTokenHistoryFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<TokenHistoryListResponse>, t: Throwable) {
                callBack.onGetTokenHistoryFailed(t.message!!)
            }
        })
        return data!!
    }

}