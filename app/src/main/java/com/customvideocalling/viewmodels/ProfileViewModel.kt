package com.customvideocalling.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.view.View
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.ProfileResponse
import com.customvideocalling.repositories.ProfileRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ProfileViewModel : BaseViewModel() {
   // private var data : MutableLiveData<LoginResponse>? = null
    private var profileDetail = MutableLiveData<ProfileResponse>()
    private var profileRepository = ProfileRepository()
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val btnClick = MutableLiveData<String>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            profileDetail = profileRepository.getUserProfile("")
           // data = profileRepository.updateUserProfile(null, null, null, null)
        }
    }

    fun getDetail() : LiveData<ProfileResponse> {
        return profileDetail!!
    }

    /*fun getUpdateDetail() : LiveData<LoginResponse> {
        return data!!
    }*/

    override fun isLoading() : LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick() : LiveData<String> {
        return btnClick
    }

    override fun clickListener(v : View) {
        btnClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    fun getProfileDetail(userid : String) {
        if (UtilsFunctions.isNetworkConnected()) {
            profileDetail = profileRepository.getUserProfile(userid)
            mIsUpdating.postValue(true)
        }

    }

    /*fun updateProfile(
        hashMap: HashMap<String, RequestBody>,
        image: MultipartBody.Part?,
        licenseimage: MultipartBody.Part?,
        otherImagee: MultipartBody.Part?
    ) {
        if (UtilsFunctions.isNetworkConnected()) {
            data = profileRepository.updateUserProfile(hashMap, image,licenseimage,otherImagee)
            mIsUpdating.postValue(true)

        }
    }*/

}