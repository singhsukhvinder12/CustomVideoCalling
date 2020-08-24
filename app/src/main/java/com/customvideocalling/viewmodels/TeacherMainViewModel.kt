//* Created by Saira on 03/07/2019.
package com.customvideocalling.viewmodels

import android.annotation.SuppressLint
import android.os.AsyncTask
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.customvideocalling.model.RideListModel
import com.customvideocalling.repositories.RideRepository

@SuppressLint("StaticFieldLeak")
class TeacherMainViewModel : ViewModel() {
    private var rideRepository = RideRepository()
    private var onLineList: MutableLiveData<RideListModel>?=null
    private val btnClick = MutableLiveData<String>()
    var offlineList:MutableLiveData<List<RideListModel.Datum>>?=null
    private val pageCount = "1"
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClicked = MutableLiveData<String>()


    val list: LiveData<RideListModel>?
        get() = onLineList


    val historyList: LiveData<List<RideListModel.Datum>>?
        get() = offlineList


    val loading: LiveData<Boolean>
        get() = mIsUpdating

    /*val isClick: LiveData<String>
        get() = isClicked*/

    init {
        paginate(pageCount)
        offlineList = MutableLiveData()

    }

    fun paginate(page: String) {
        mIsUpdating.postValue(true)
        onLineList = rideRepository.getaRideHistory(page)
       // allList=MutableLiveData()
    }

    fun onClickMethods(view: View) {
        isClicked.postValue("btn")
    }


    fun getHistoryList()  {
        object : AsyncTask<Void, Void, Void>() {
            override fun doInBackground(vararg voids: Void): Void? {
              //  offlineList!!.postValue(MyApplication.appDatabase.historyDao().getAll())
                return null
            }
        }.execute()

    }
     fun isClick() : LiveData<String> {
        return btnClick
    }

     fun clickListener(v : View) {
        btnClick.postValue(v.resources.getResourceName(v.id).split("/")[1])
    }

}