package com.customvideocalling.views.teacher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.adapters.ScheduleDetailExpandableAdapter
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityAddScheduleBinding
import com.customvideocalling.databinding.ActivityScheduleDetailBinding
import com.customvideocalling.databinding.ActivityScheduleListBinding
import com.customvideocalling.model.ScheduleDetailResponse
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.AddScheduleViewModel
import com.example.artha.model.CommonModel
import com.google.gson.JsonObject
import com.uniongoods.adapters.ScheduleListAdapter

class ScheduleDetailActivity : BaseActivity(), View.OnClickListener, CallBackResult.ScheduleDetailCallBack {
    private lateinit var activityScheduleDetailBinding: ActivityScheduleDetailBinding
    private lateinit var addScheduleViewModel: AddScheduleViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var fromDate = "2020-08-29"
    private var endDate = "2020-08-31"
    private var adapter: ScheduleDetailExpandableAdapter?=null
    private var scheduleDetailParent: ArrayList<ScheduleDetailResponse.Result>?=null


    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_detail
    }

    override fun initViews() {
        activityScheduleDetailBinding=viewDataBinding as ActivityScheduleDetailBinding
        addScheduleViewModel = ViewModelProviders.of(this).get(AddScheduleViewModel::class.java)
        activityScheduleDetailBinding!!.toolBar.toolbarText.setText(getString(R.string.schedule_detail))
        activityScheduleDetailBinding!!.toolBar.toolbarBack.setOnClickListener(this)
        scheduleDetailParent = ArrayList()
        //initRecyclerView()
        val mJsonObject = JsonObject()
        mJsonObject.addProperty("teacherId", sharedPrefClass.getPrefValue(this,GlobalConstants.USERID).toString())
        mJsonObject.addProperty("fromDate", fromDate)
        mJsonObject.addProperty("toDate", endDate)
        addScheduleViewModel.scheduleDetailAPi(this, mJsonObject)
    }

    override fun onClick(p0: View?) {

    }

    private fun initRecyclerView() {
        adapter =
            ScheduleDetailExpandableAdapter(scheduleDetailParent)
        activityScheduleDetailBinding.scheduleDetailList.adapter = adapter

    }

    override fun onScheduleDetailSuccess(response: ScheduleDetailResponse) {
        scheduleDetailParent = response.result
        initRecyclerView()
    }

    override fun onScheduleDetailFailed(message: String) {
    showToastError(message)
    }
}