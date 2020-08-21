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
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.model.SlotListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddTokenRepository {
    private var data : MutableLiveData<PlanListResponse>? = null
    private var dataAddBooking : MutableLiveData<CommonModel>? = null
    private var dataSlotList : MutableLiveData<SlotListResponse>? = null


    init {
        data = MutableLiveData()
        dataAddBooking = MutableLiveData()
        dataSlotList = MutableLiveData()

    }

    fun getPlanList(callBack: CallBackResult.PlanListCallBack): MutableLiveData<PlanListResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getPlanList()
        call.enqueue(object : Callback<PlanListResponse> {
            override fun onResponse(
                call: Call<PlanListResponse>,
                response: retrofit2.Response<PlanListResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onGetPlanListSuccess(response.body()!!)
                    }
                    else {
                        callBack.onGetPlanListFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onGetPlanListFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<PlanListResponse>, t: Throwable) {
                callBack.onGetPlanListFailed(t.message!!)
            }
        })
        return data!!
    }

    fun hitPaymentApi(callBack: CallBackResult.PaymentCallBack,  mJsonObject: JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).addToken(mJsonObject)
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: retrofit2.Response<CommonModel>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onPaymentSuccess(response.body()!!)
                    }
                    else {
                        callBack.onPaymentFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onPaymentFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                callBack.onPaymentFailed(t.message!!)
            }
        })
        return dataAddBooking!!
    }

}