package com.customvideocalling.views.teacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityTeacherNotificationListBinding
import com.customvideocalling.databinding.ActivityTokenHistoryBinding
import com.customvideocalling.model.TeacherNotificationListResponse
import com.customvideocalling.model.TokenHistoryListResponse
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.TeacherNotificationViewModel
import com.customvideocalling.viewmodels.TokenHistoryViewModel
import com.example.artha.model.CommonModel
import com.google.gson.JsonObject
import com.uniongoods.adapters.TeacherNotificationListAdapter
import com.uniongoods.adapters.TokeHistoryListAdapter

class TeacherNotificationListActivity : BaseActivity(), View.OnClickListener, CallBackResult.TeacherNotificationListCallBack,
CallBackResult.TeacherAcceptRejectCallBack, CallBackResult.TeacherAcceptRejectApiCallBack{
    private lateinit var activityTeacherNotificationListBinding: ActivityTeacherNotificationListBinding
    private lateinit var teacherNotificationViewModel: TeacherNotificationViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var notificationList: ArrayList<TeacherNotificationListResponse.Result>?=null
    private var teacherNotificationListAdapter : TeacherNotificationListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_teacher_notification_list
    }

    override fun initViews() {
        activityTeacherNotificationListBinding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_notification_list)
        teacherNotificationViewModel = ViewModelProviders.of(this).get(TeacherNotificationViewModel::class.java)
        activityTeacherNotificationListBinding.toolBar.toolbarText.setText(getString(R.string.requests))
        activityTeacherNotificationListBinding.toolBar.toolbarBack.setOnClickListener(this)
        notificationList = ArrayList()
        teacherNotificationViewModel.getNotificationListTeacherList(this, sharedPrefClass.getPrefValue(this,
            GlobalConstants.USERID).toString())

    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.toolbar_back -> {
                finish()
            }
        }
    }

    private fun initRecyclerView() {
        teacherNotificationListAdapter =
            TeacherNotificationListAdapter(this, notificationList!!, this!!, this)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        activityTeacherNotificationListBinding.rvBookingTeacher.layoutManager = linearLayoutManager
        activityTeacherNotificationListBinding.rvBookingTeacher.adapter = teacherNotificationListAdapter
        activityTeacherNotificationListBinding.rvBookingTeacher.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    override fun onClickAcceptReject(pos: Int, status: Int) {
        startProgressDialog()
        val mJsonObject = JsonObject()
        mJsonObject.addProperty("status", status)
        mJsonObject.addProperty("bookingId", notificationList!![pos].bookId)
        mJsonObject.addProperty("teacherId", notificationList!![pos].teacherId)
        mJsonObject.addProperty("channelName", "")
        mJsonObject.addProperty("accessToken", "")
        teacherNotificationViewModel.hitAcceptRejectApi(this,mJsonObject)
    }

    override fun onTeacherNotificationListSuccess(response: TeacherNotificationListResponse) {
        notificationList = response.result!!
        if (notificationList != null && notificationList!!.size>0) {
            activityTeacherNotificationListBinding.rvBookingTeacher.visibility = View.VISIBLE
            activityTeacherNotificationListBinding.tvNoRecord.visibility = View.GONE
        }else{
            activityTeacherNotificationListBinding.rvBookingTeacher.visibility = View.GONE
            activityTeacherNotificationListBinding.tvNoRecord.visibility = View.VISIBLE
        }
        initRecyclerView()
    }

    override fun oTeacherNotificationListFailed(message: String) {
      showToastError(message)
    }

    override fun onTeacherAcceptRejectSuccess(response: CommonModel) {
        stopProgressDialog()
        teacherNotificationViewModel.getNotificationListTeacherList(this, sharedPrefClass.getPrefValue(this,
            GlobalConstants.USERID).toString())
    }

    override fun onTeacherAcceptRejectFailed(message: String) {
        stopProgressDialog()
        showToastError(message)
    }
}