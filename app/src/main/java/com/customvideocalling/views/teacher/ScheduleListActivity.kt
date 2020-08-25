package com.customvideocalling.views.teacher

import android.content.Intent
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
import com.customvideocalling.databinding.ActivityScheduleListBinding
import com.customvideocalling.model.ScheduleListResponse
import com.customvideocalling.model.TokenHistoryListResponse
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.AddScheduleViewModel
import com.customvideocalling.viewmodels.TokenHistoryViewModel
import com.uniongoods.adapters.ScheduleListAdapter
import com.uniongoods.adapters.TokeHistoryListAdapter

class ScheduleListActivity : BaseActivity(), View.OnClickListener, CallBackResult.ScheduleListCallBack {
    private lateinit var activityScheduleListBinding: ActivityScheduleListBinding
    private lateinit var addScheduleViewModel: AddScheduleViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var scheduleList: ArrayList<ScheduleListResponse.Result>?=null
    private var scheduleListAdapter : ScheduleListAdapter? = null


    override fun getLayoutId(): Int {
        return R.layout.activity_schedule_list
    }

    override fun initViews() {
        activityScheduleListBinding = DataBindingUtil.setContentView(this, R.layout.activity_schedule_list)
        addScheduleViewModel = ViewModelProviders.of(this).get(AddScheduleViewModel::class.java)
        activityScheduleListBinding.toolBar.toolbarText.setText("Schedules")
        activityScheduleListBinding.toolBar.toolbarBack.setOnClickListener(this)
        activityScheduleListBinding.floatAddSchedule.setOnClickListener(this)
        scheduleList = ArrayList()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.toolbar_back -> {
                finish()
            }
            R.id.float_add_schedule -> {
                val intent = Intent(this, AddScheduleActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        addScheduleViewModel.getScheduleList(
            this,
            sharedPrefClass.getPrefValue(this, GlobalConstants.USERID).toString()
        )
    }

    private fun initRecyclerView() {
        scheduleListAdapter =
            ScheduleListAdapter(this, scheduleList!!, this!!)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        activityScheduleListBinding.rvSchedule.layoutManager = linearLayoutManager
        activityScheduleListBinding.rvSchedule.adapter = scheduleListAdapter
        activityScheduleListBinding.rvSchedule.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    override fun onScheduleListSuccess(response: ScheduleListResponse) {
        scheduleList = response.result!!
        if (scheduleList != null && scheduleList!!.size>0) {
            activityScheduleListBinding.rvSchedule.visibility = View.VISIBLE
            activityScheduleListBinding.tvNoRecord.visibility = View.GONE
        }else{
            activityScheduleListBinding.rvSchedule.visibility = View.GONE
            activityScheduleListBinding.tvNoRecord.visibility = View.VISIBLE
        }
        initRecyclerView()
    }

    override fun onScheduleListFailed(message: String) {
        showToastError(message)
    }
}