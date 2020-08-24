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

class TeacherNotificationViewModel : BaseViewModel() {
    private var data: MutableLiveData<TeacherNotificationListResponse>? = null
    private var data1: MutableLiveData<CommonModel>? = null
    private var teacherNotificationRepository = TeacherNotificationRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()


    fun getList(): LiveData<TeacherNotificationListResponse> {
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

    fun getNotificationListTeacherList(callBack: CallBackResult.TeacherNotificationListCallBack, userId: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            data = teacherNotificationRepository.getTeacherNotificationList(callBack, userId)
            mIsUpdating.postValue(true)
        }
    }

    fun hitAcceptRejectApi(callBack: CallBackResult.TeacherAcceptRejectApiCallBack, jsonObject:JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            data1 = teacherNotificationRepository.hitAcceptRejectApi(callBack, jsonObject)
            mIsUpdating.postValue(true)
        }
    }

}