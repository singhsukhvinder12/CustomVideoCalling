package com.secretmenu.repositories

import androidx.lifecycle.MutableLiveData
import com.example.artha.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.secretmenu.R
import com.secretmenu.api.ApiClient
import com.secretmenu.api.ApiResponse
import com.secretmenu.api.ApiService
import com.secretmenu.application.MyApplication
import com.secretmenu.common.UtilsFunctions
import com.secretmenu.model.LoginResponse
import retrofit2.Response

class LoginRepository {
    private var data : MutableLiveData<LoginResponse>? = null
    private var data1 : MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        data1 = MutableLiveData()

    }

    fun getLoginData(jsonObject : JsonObject?) : MutableLiveData<LoginResponse> {
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
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().callLogin(jsonObject)

            )

        }
        return data!!

    }

    fun getLogoutResonse(jsonObject : JsonObject?) : MutableLiveData<CommonModel> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse : Response<JsonObject>) {
                        val logoutResponse = if (mResponse.body() != null)
                            gson.fromJson<CommonModel>("" + mResponse.body(), CommonModel::class.java)
                        else {
                            gson.fromJson<CommonModel>(
                                mResponse.errorBody()!!.charStream(),
                                CommonModel::class.java
                            )
                        }

                        data1!!.postValue(logoutResponse)

                    }

                    override fun onError(mKey : String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().callLogout(jsonObject)

            )

        }
        return data1!!

    }

}