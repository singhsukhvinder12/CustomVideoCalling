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
import com.customvideocalling.model.JobsResponse
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import retrofit2.Response

class HomeJobsRepository {
    private var data: MutableLiveData<JobsResponse>? = null
    private val gson = GsonBuilder().serializeNulls().create()

    init {
        data = MutableLiveData()
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
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data!!.postValue(null)

                        }

                    }, GetRestAdapter.getRestAdapter(false).getStudentBooings(userid)
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
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data!!.postValue(null)

                        }

                        // }, ApiClient.getApiInterface().getJobsHistory(mJsonObject)
                    }, GetRestAdapter.getRestAdapter(false).getStudentHistoryBooings(userid)
            )
        }
        return data!!
    }

   /* fun acceptRejectJob(mJsonObject: JsonObject?): MutableLiveData<CommonModel> {
        if (mJsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
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

                            data1!!.postValue(loginResponse)

                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data1!!.postValue(null)

                        }

                    }, ApiClient.getApiInterface().acceptRejectJob(mJsonObject)
            )
        }
        return data1!!
    }*/

   /* fun startCompleteJob(mJsonObject: JsonObject?): MutableLiveData<CommonModel> {
        if (mJsonObject != null) {
            val mApiService = ApiService<JsonObject>()
            mApiService.get(
                    object : ApiResponse<JsonObject> {
                        override fun onResponse(mResponse: Response<JsonObject>) {
                            val loginResponse = if (mResponse.body() != null)
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

                            data1!!.postValue(loginResponse)

                        }

                        override fun onError(mKey: String) {
                            UtilsFunctions.showToastError(MyApplication.instance.getString(R.string.internal_server_error))
                            data1!!.postValue(null)

                        }

                    }, ApiClient.getApiInterface().startCompleteJob(mJsonObject)
            )
        }
        return data1!!
    }*/

}