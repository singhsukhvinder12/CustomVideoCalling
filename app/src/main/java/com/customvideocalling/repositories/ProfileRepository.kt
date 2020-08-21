package com.customvideocalling.repositories

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.customvideocalling.R
import com.customvideocalling.api.ApiClient
import com.customvideocalling.api.ApiResponse
import com.customvideocalling.api.ApiService
import com.customvideocalling.api.GetRestAdapter
import com.customvideocalling.application.MyApplication
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.ProfileResponse

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import java.util.HashMap

class ProfileRepository {
  //  private var data: MutableLiveData<LoginResponse>? = null
    private var data1: MutableLiveData<ProfileResponse>? = null
  //  private var data2: MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
       // data = MutableLiveData()
        data1 = MutableLiveData()
      //  data2 = MutableLiveData()

    }

   /* fun getLoginData(jsonObject: JsonObject?): MutableLiveData<LoginResponse> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<LoginResponse>(
                                "" + mResponse.body(),
                                LoginResponse::class.java
                            )
                        else {
                            gson.fromJson<LoginResponse>(
                                mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().callLogin(jsonObject)
            )

        }
        return data!!

    }*/

    fun getUserProfile(userid: String): MutableLiveData<ProfileResponse> {
        if (!TextUtils.isEmpty(userid)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<ProfileResponse>(
                                "" + mResponse.body(),
                                ProfileResponse::class.java
                            )
                        else {
                            gson.fromJson<ProfileResponse>(
                                mResponse.errorBody()!!.charStream(),
                                ProfileResponse::class.java
                            )
                        }
                        data1!!.postValue(loginResponse)
                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)
                    }

                }, GetRestAdapter.getRestAdapter(false).getProfile(userid)
            )

        }
        return data1!!

    }

   /* fun getLogoutResonse(jsonObject: JsonObject?): MutableLiveData<CommonModel> {
        if (jsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val logoutResponse = if (mResponse.body() != null)
                            gson.fromJson<CommonModel>(
                                "" + mResponse.body(),
                                CommonModel::class.java
                            )
                        else {
                            gson.fromJson<CommonModel>(
                                mResponse.errorBody()!!.charStream(),
                                CommonModel::class.java
                            )
                        }

                        data2!!.postValue(logoutResponse)

                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data1!!.postValue(null)

                    }

                }, ApiClient.getApiInterface().callLogout(*//*jsonObject*//*)
            )

        }
        return data2!!

    }*/

    /*fun updateUserProfile(
        hashMap: HashMap<String, RequestBody>?,
        image: MultipartBody.Part?,
        licenseimage: MultipartBody.Part?,
        otherImagee: MultipartBody.Part?
    ): MutableLiveData<LoginResponse> {
        if (hashMap != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                object : ApiResponse<JsonObject> {
                    override fun onResponse(mResponse: Response<JsonObject>) {
                        val loginResponse = if (mResponse.body() != null)
                            gson.fromJson<LoginResponse>(
                                "" + mResponse.body(),
                                LoginResponse::class.java
                            )
                        else {
                            gson.fromJson<LoginResponse>(
                                mResponse.errorBody()!!.charStream(),
                                LoginResponse::class.java
                            )
                        }


                        data!!.postValue(loginResponse)

                    }

                    override fun onError(mKey: String) {
                        UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                        data!!.postValue(null)

                    }

                },
                ApiClient.getApiInterface().callUpdateProfile(
                    hashMap,
                    image,
                    licenseimage,
                    otherImagee
                )
            )
        }
        return data!!

    }*/

}