package com.customvideocalling.repositories

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.api.ApiClient
import com.customvideocalling.api.ApiResponse
import com.customvideocalling.api.ApiService
import com.customvideocalling.api.GetRestAdapter
import com.customvideocalling.application.MyApplication
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.JobsResponse
import com.example.artha.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TeacherHomeJobsRepository {
    private var data: MutableLiveData<JobsResponse>? = null
    private var data1: MutableLiveData<CommonModel>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
        data1 = MutableLiveData()
    }

    fun getMyJobsList(userid: String?): MutableLiveData<JobsResponse> {
        if (userid != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
                                gson.fromJson<JobsResponse>(
                                        "" + mResponse.body(),
                                        JobsResponse::class.java
                                )
                            else {
                                gson.fromJson<JobsResponse>(
                                        mResponse.errorBody()!!.charStream(),
                                        JobsResponse::class.java
                                )
                            }

                            data!!.postValue(loginResponse)

                        }

                        override fun onError(mKey: String) {
                          //  UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data!!.postValue(null)

                        }

                    }, GetRestAdapter.getRestAdapter(false).getTeacherLiveBookings(userid)
            )
        }
        return data!!
    }

    fun getMyJobsHistoryList(userid: String): MutableLiveData<JobsResponse> {
        if (!TextUtils.isEmpty(userid)) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
                                gson.fromJson<JobsResponse>(
                                        "" + mResponse.body(),
                                        JobsResponse::class.java
                                )
                            else {
                                gson.fromJson<JobsResponse>(
                                        mResponse.errorBody()!!.charStream(),
                                        JobsResponse::class.java
                                )
                            }

                            data!!.postValue(loginResponse)

                        }

                        override fun onError(mKey: String) {
                       //     UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data!!.postValue(null)

                        }

                        // }, ApiClient.getApiInterface().getJobsHistory(mJsonObject)
                    }, GetRestAdapter.getRestAdapter(false).getTeacherHistoryBookings(userid)
            )
        }
        return data!!
    }

    fun startCallApi(callBack: CallBackResult.StartCallApiCallBack, mJsonObject: JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).startCallApi(mJsonObject)
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: retrofit2.Response<CommonModel>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onStartCallApiSuccess(response.body()!!)
                    }
                    else {
                        callBack.onStartCallApiFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onStartCallApiFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                callBack.onStartCallApiFailed(t.message!!)
            }
        })
        return data1!!
    }

    fun endCallApi(callBack: CallBackResult.EndCallApiCallBack, mJsonObject: JsonObject): MutableLiveData<CommonModel> {
        val call = GetRestAdapter.getRestAdapter(false).endCallApi(mJsonObject)
        call.enqueue(object : Callback<CommonModel> {
            override fun onResponse(
                call: Call<CommonModel>,
                response: retrofit2.Response<CommonModel>?
            ) {
                if (response?.body() != null) {
                    if (response?.body()!!.code == 200) {
                        callBack.onEndCallApiSuccess(response.body()!!)
                    }
                    else {
                        callBack.onEndCallApiFailed(response?.body()?.message!!)
                    }
                } else {
                    callBack.onEndCallApiFailed("Something went wrong")
                }

            }

            override fun onFailure(call: Call<CommonModel>, t: Throwable) {
                callBack.onEndCallApiFailed(t.message!!)
            }
        })
        return data1!!
    }

}