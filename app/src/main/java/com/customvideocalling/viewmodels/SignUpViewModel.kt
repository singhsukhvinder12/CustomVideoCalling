package com.customvideocalling.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.customvideocalling.Interfaces.CallBackResult
import com.google.gson.JsonObject
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.model.facultyDesignationResponse
import com.customvideocalling.repositories.LoginRepository
import com.customvideocalling.repositories.SignUpRepository
import com.customvideocalling.views.authentication.TeacherSignupActivity
import com.example.artha.model.CommonModel

class SignUpViewModel : BaseViewModel() {
    private var dataStudent: MutableLiveData<LoginResponse>? = null
    private var dataTeacher: MutableLiveData<LoginResponse>? = null
    private var data1: MutableLiveData<CommonModel>? = null
    private var dataFaculty: MutableLiveData<facultyDesignationResponse>? = null
    private var dataSubject: MutableLiveData<ClassSubjectListResponse>? = null
    private var signUpRepository = SignUpRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()
    private val activity: TeacherSignupActivity?= TeacherSignupActivity()

    init {
        dataStudent = signUpRepository.getStudentSignUpData(null)
        dataTeacher = signUpRepository.getTeacherSignUpData(null, activity!!)

    }

    fun getList(): LiveData<LoginResponse> {
        return dataStudent!!
    }

    fun getListTeacher(): LiveData<LoginResponse> {
        return dataTeacher!!
    }

    override fun isLoading(): LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick(): LiveData<String> {
        return btnClick
    }

    override fun clickListener(v: View) {
        btnClick.postValue(v.resources.getResourceName(v.id).split("/")[1])    }

    fun hitStudentSignUpApi(mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            dataStudent = signUpRepository.getStudentSignUpData(mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun hitTeacherSignUpApi(mJsonObject: JsonObject, activity: TeacherSignupActivity) {
        if (UtilsFunctions.isNetworkConnected()) {
            dataTeacher = signUpRepository.getTeacherSignUpData(mJsonObject, activity)
            mIsUpdating.postValue(true)
        }
    }

    fun addDeviceToken(callBack: CallBackResult.AddDeviceTokenCallBack, mJsonObject: JsonObject) {
        if (UtilsFunctions.isNetworkConnected()) {
            data1 = signUpRepository.addDeviceToken(callBack, mJsonObject)
            mIsUpdating.postValue(true)
        }
    }

    fun getFacultyRegisterList(callBack: CallBackResult.FacultyDesignationCallBack) {
        if (UtilsFunctions.isNetworkConnected()) {
            dataFaculty = signUpRepository.getFacultyDesignation(callBack)
            mIsUpdating.postValue(true)
        }
    }

    fun getSubjectList(callBack: CallBackResult.ClassSubjectListCallBack) {
        if (UtilsFunctions.isNetworkConnected()) {
            dataSubject = signUpRepository.getClassSubjectList(callBack)
            mIsUpdating.postValue(true)
        }
    }
}