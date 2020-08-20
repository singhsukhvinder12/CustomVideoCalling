package com.customvideocalling.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.customvideocalling.Interfaces.CallBackResult
import com.google.gson.JsonObject
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.model.SlotListResponse
import com.customvideocalling.repositories.AddBookingStudentRepository
import com.customvideocalling.repositories.LoginRepository
import com.example.artha.model.CommonModel

class BookingAddStudentViewModel : BaseViewModel() {
    private var data: MutableLiveData<ClassSubjectListResponse>? = null
    private var dataAddBooking: MutableLiveData<CommonModel>? = null
    private var dataSlotList: MutableLiveData<SlotListResponse>? = null
    private var addBookingStudentRepository = AddBookingStudentRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()


    fun getList(): LiveData<ClassSubjectListResponse> {
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

    fun getSubjectList(callBack: CallBackResult.ClassSubjectListCallBack) {
        if (UtilsFunctions.isNetworkConnected()) {
            data = addBookingStudentRepository.getClassSubjectList(callBack)
            mIsUpdating.postValue(true)
        }
    }

    fun getSlotList(callBack: CallBackResult.SlotListCallBack, mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            dataSlotList = addBookingStudentRepository.getSlotList(callBack, mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun hitAddBookingApi(callBack: CallBackResult.AddBookingCallBack, mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            dataAddBooking = addBookingStudentRepository.addBooking(callBack,mJsonObject)
            mIsUpdating.postValue(true)
        }
    }
}