package com.customvideocalling.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.customvideocalling.Interfaces.CallBackResult
import com.google.gson.JsonObject
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.model.SlotListResponse
import com.customvideocalling.repositories.AddBookingStudentRepository
import com.customvideocalling.repositories.AddTokenRepository
import com.customvideocalling.repositories.LoginRepository
import com.example.artha.model.CommonModel

class AddTokenViewModel : BaseViewModel() {
    private var data: MutableLiveData<PlanListResponse>? = null
    private var dataPayment: MutableLiveData<CommonModel>? = null
    private var dataSlotList: MutableLiveData<SlotListResponse>? = null
    private var addTokenRepository = AddTokenRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()


    fun getList(): LiveData<PlanListResponse> {
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

    fun getPlanList(callBack: CallBackResult.PlanListCallBack) {
        if (UtilsFunctions.isNetworkConnected()) {
            data = addTokenRepository.getPlanList(callBack)
            mIsUpdating.postValue(true)
        }
    }


    fun hitPaymentApi(callBack: CallBackResult.PaymentCallBack, mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            dataPayment = addTokenRepository.hitPaymentApi(callBack,mJsonObject)
            mIsUpdating.postValue(true)
        }
    }
}