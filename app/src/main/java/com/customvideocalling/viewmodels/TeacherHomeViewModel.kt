package com.customvideocalling.viewmodels

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.model.JobsResponse
import com.customvideocalling.repositories.HomeJobsRepository
import com.customvideocalling.repositories.TeacherHomeJobsRepository
import com.google.gson.JsonObject

class TeacherHomeViewModel : BaseViewModel() {
    private val mIsUpdating = MutableLiveData<Boolean>()
    private val isClick = MutableLiveData<String>()
    private var teacherHomeRepository = TeacherHomeJobsRepository()
    private var jobsListResponse = MutableLiveData<JobsResponse>()
    private var jobsHistoryResponse = MutableLiveData<JobsResponse>()
    //  private var acceptRejectJob = MutableLiveData<CommonModel>()
    //  private var startCompleteJob = MutableLiveData<CommonModel>()

    init {
        if (UtilsFunctions.isNetworkConnectedWithoutToast()) {
            jobsListResponse = teacherHomeRepository.getMyJobsList(null)
            jobsHistoryResponse = teacherHomeRepository.getMyJobsHistoryList("")
            //  acceptRejectJob = homeRepository.acceptRejectJob(null)
            //  startCompleteJob = homeRepository.startCompleteJob(null)
        }

    }

    fun getJobs(): LiveData<JobsResponse> {
        return jobsListResponse
    }

    fun getJobsHistory(): LiveData<JobsResponse> {
        return jobsHistoryResponse
    }


    override fun isLoading(): LiveData<Boolean> {
        return mIsUpdating
    }

    override fun isClick(): LiveData<String> {
        return isClick
    }

    override fun clickListener(v: View) {
        isClick.value = v.resources.getResourceName(v.id).split("/")[1]
    }

    fun getMyJobs(userId: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            jobsListResponse = teacherHomeRepository.getMyJobsList(userId)
            mIsUpdating.postValue(true)
        }

    }

    fun getMyJobsHistory(userid: String) {
        if (UtilsFunctions.isNetworkConnected()) {
            jobsListResponse = teacherHomeRepository.getMyJobsHistoryList(userid)
            mIsUpdating.postValue(true)
        }
    }
}