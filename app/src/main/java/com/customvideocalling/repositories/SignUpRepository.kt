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
import com.customvideocalling.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpRepository {
    private var data : MutableLiveData<LoginResponse>? = null
    private var data1 : MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        data1 = MutableLiveData()

    }

    fun getTeacherSignUpData(jsonObject : JsonObject?) : MutableLiveData<LoginResponse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<LoginResponse>("" + mResponse.body(), LoginResponse::class.java)
                        else {
                            gson.fromJson<LoginResponse>(
                                 mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey : String) {
                       // UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, GetRestAdapter.getRestAdapter(false).callTeacherSignUp(jsonObject)

            )

        }
        return data!!

    }

    fun getStudentSignUpData(jsonObject : JsonObject?) : MutableLiveData<LoginResponse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<LoginResponse>("" + mResponse.body(), LoginResponse::class.java)
                        else {
                            gson.fromJson<LoginResponse>(
                                mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey : String) {
                       // UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, GetRestAdapter.getRestAdapter(false).callStudentSignUp(jsonObject)

            )

        }
        return data!!

    }

    fun addDeviceToken(callBack: CallBackResult.AddDeviceTokenCallBack, mJsonObject: JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).addDeviceToken(mJsonObject)
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: retrofit2.Response<CommonModel>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onAddDeviceTokenSuccess(response.body()!!)
                    }
                    else {
                        callBack.onAddDeviceTokenFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onAddDeviceTokenFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                callBack.onAddDeviceTokenFailed(t.message!!)
            }
        })
        return data1!!
    }
}