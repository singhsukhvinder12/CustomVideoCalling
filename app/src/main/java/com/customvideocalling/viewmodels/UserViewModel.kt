//* Created by Saira on 03/07/2019.
package com.customvideocalling.viewmodels

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.customvideocalling.repositories.UserRepository
import com.customvideocalling.roomdatabase.User

@SuppressLint("StaticFieldLeak")
class UserViewModel : ViewModel() {
    private var allList: MutableLiveData<List<User>>? = null
    private val pageCount = "1"
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClicked = MutableLiveData<String>()
    private var userRepository= UserRepository()

    init {

        allList = MutableLiveData()
        getAllList()


    }

    val list: LiveData<List<User>>?
        get() = allList
    val loading: LiveData<Boolean>
        get() = mIsUpdating
    val isClick: LiveData<String>
        get() = isClicked

    fun paginate(page: String) {
        mIsUpdating.postValue(true)

    }

    fun uplaodEntry(uid:Int,jsonObject: JsonObject)
    {
        userRepository.uploadUserEntry(uid,jsonObject)
    }
    fun getAllList()
    {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
           //     allList!!.postValue(MyApplication.appDatabase.userDao().getAll())
                return null
            }
        }.execute()
    }
    fun onClickMethods(v: View) {
        isClicked.postValue(v.resources.getResourceName(v.id).split("/")[1])
    }

}