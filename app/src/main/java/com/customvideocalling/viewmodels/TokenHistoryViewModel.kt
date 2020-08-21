package com.customvideocalling.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.customvideocalling.Interfaces.CallBackResult
import com.google.gson.JsonObject
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.*
import com.customvideocalling.repositories.AddBookingStudentRepository
import com.customvideocalling.repositories.AddTokenRepository
import com.customvideocalling.repositories.LoginRepository
import com.customvideocalling.repositories.TokenHistoryRepository
import com.example.artha.model.CommonModel

class TokenHistoryViewModel : BaseViewModel() {
    private var data: MutableLiveData<TokenHistoryListResponse>? = null
    private var tokenHistoryRepository = TokenHistoryRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()


    fun getList(): LiveData<TokenHistoryListResponse> {
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

    fun getTokenHistoryList(callBack: CallBackResult.TokenHistoryCallBack, userId: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            data = tokenHistoryRepository.getTokenHistoryList(callBack, userId)
            mIsUpdating.postValue(true)
        }
    }

}