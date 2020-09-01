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
import com.customvideocalling.repositories.FeedBackRepository
import com.customvideocalling.repositories.LoginRepository
import com.example.artha.model.CommonModel

class FeedBackViewModel : BaseViewModel() {
    private var dataFeedBack: MutableLiveData<CommonModel>? = null
    private var feedBackRepository = FeedBackRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()


   /* fun getList(): LiveData<ClassSubjectListResponse> {
        return data!!
    }*/

    override fun isLoading(): LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick(): LiveData<String> {
        return btnClick
    }

    override fun clickListener(v: View) {
        btnClick.postValue(v.resources.getResourceName(v.id).split("/")[1])    }



    fun hitAddFeedbackApi(callBack: CallBackResult.AddBookingCallBack, mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            dataFeedBack = feedBackRepository.addFeedback(callBack,mJsonObject)
            mIsUpdating.postValue(true)
        }
    }
}