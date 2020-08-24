package com.customvideocalling.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.customvideocalling.Interfaces.CallBackResult
import com.google.gson.JsonObject
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.*
import com.customvideocalling.repositories.*
import com.example.artha.model.CommonModel

class AddScheduleViewModel : BaseViewModel() {
    private var data: MutableLiveData<ScheduleListResponse>? = null
    private var data1: MutableLiveData<CommonModel>? = null
    private var addScheduleRepository = AddScheduleRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()


    fun getList(): LiveData<ScheduleListResponse> {
        return data!!
    }

    override fun isLoading(): LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick(): LiveData<String> {
        return btnClick
    }

    override fun clickListener(v: View) {
        btnClick.postValue(v.resources.getResourceName(v.id).split("/")[1])    }

    fun getScheduleList(callBack: CallBackResult.ScheduleListCallBack, userId: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            data = addScheduleRepository.getScheduleList(callBack, userId)
            mIsUpdating.postValue(true)
        }
    }

    fun hitAddSchedule(callBack: CallBackResult.AddScheduleCallBack, jsonObject:JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            data1 = addScheduleRepository.hitAddSchedule(callBack, jsonObject)
            mIsUpdating.postValue(true)
        }
    }

}