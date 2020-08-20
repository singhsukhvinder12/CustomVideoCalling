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

class AddBookingStudentRepository {
    private var data : MutableLiveData<ClassSubjectListResponse>? = null
    private var dataAddBooking : MutableLiveData<CommonModel>? = null
    private var dataSlotList : MutableLiveData<SlotListResponse>? = null


    init {
        data = MutableLiveData()
        dataAddBooking = MutableLiveData()
        dataSlotList = MutableLiveData()

    }

    fun getClassSubjectList(callBack: CallBackResult.ClassSubjectListCallBack): MutableLiveData<ClassSubjectListResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getClassSubject()
        call.enqueue(object : Callback<ClassSubjectListResponse> {
            override fun onResponse(
                call: Call<ClassSubjectListResponse>,
                response: retrofit2.Response<ClassSubjectListResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onGetClassSubjectListSuccess(response.body()!!)
                    }
                    else {
                        callBack.onGetClassSubjectListFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onGetClassSubjectListFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<ClassSubjectListResponse>, t: Throwable) {
                callBack.onGetClassSubjectListFailed(t.message!!)
            }
        })
        return data!!
    }

    fun getSlotList(callBack: CallBackResult.SlotListCallBack, mJsonObject: JsonObject): MutableLiveData<SlotListResponse> {
        val call = GetRestAdapter.getRestAdapter(false).getSlotList(mJsonObject)
        call.enqueue(object : Callback<SlotListResponse> {
            override fun onResponse(
                call: Call<SlotListResponse>,
                response: retrofit2.Response<SlotListResponse>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onGetSlotListSuccess(response.body()!!)
                    }
                    else {
                        callBack.onGetSlotListFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onGetSlotListFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<SlotListResponse>, t: Throwable) {
                callBack.onGetSlotListFailed(t.message!!)
            }
        })
        return dataSlotList!!
    }

    fun addBooking(callBack: CallBackResult.AddBookingCallBack,  mJsonObject: JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).addBookingStudent(mJsonObject)
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
        return dataAddBooking!!
    }

}