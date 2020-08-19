package com.customvideocalling.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.JsonObject
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.repositories.LoginRepository

class LoginViewModel : BaseViewModel() {
    private var data: MutableLiveData<LoginResponse>? = null
    private var loginRepository = LoginRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        data = loginRepository.getLoginData(null)

    }

    fun getList(): LiveData<LoginResponse> {
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

    fun hitLoginApi(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            data = loginRepository.getLoginData(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }
}