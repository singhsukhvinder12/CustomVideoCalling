package com.customvideocalling.views.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.databinding.ActivityAddAppoitmentBinding
import com.customvideocalling.databinding.ActivityAddTokentBinding
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.AddTokenViewModel
import com.customvideocalling.viewmodels.BookingAddStudentViewModel
import com.uniongoods.adapters.JobRequestsAdapter
import com.uniongoods.adapters.PlanListAdapter

class AddTokentActivity : BaseActivity(), View.OnClickListener, CallBackResult.PlanListCallBack {
    private lateinit var activityAddTokentBinding: ActivityAddTokentBinding
    private lateinit var addTokenViewModel: AddTokenViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var planList: ArrayList<PlanListResponse.Result>?=null
    private var planListAdapter : PlanListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_add_tokent
    }

    override fun initViews() {
        activityAddTokentBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_tokent)
        addTokenViewModel = ViewModelProviders.of(this).get(AddTokenViewModel::class.java)
        activityAddTokentBinding.toolBar.toolbarText.setText(getString(R.string.add_token))
        activityAddTokentBinding.toolBar.toolbarBack.setOnClickListener(this)
        planList = ArrayList()
        addTokenViewModel.getPlanList(this)
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.toolbar_back -> {
                finish()
            }
        }
    }

    override fun onGetPlanListSuccess(response: PlanListResponse) {
        planList = response.result!!
        if (planList != null && planList!!.size>0) {
            val planOther = PlanListResponse.Result()
            planOther.description = "Other"
            planOther.tokenAmount = 0.0f
            planOther.id = 7
            planOther.tokenCount = 0
            planList!!.add(planOther)
            activityAddTokentBinding.rvPlan.visibility = View.VISIBLE
            activityAddTokentBinding.tvNoRecord.visibility = View.GONE
        }else{
            activityAddTokentBinding.rvPlan.visibility = View.GONE
            activityAddTokentBinding.tvNoRecord.visibility = View.VISIBLE
        }
        initRecyclerView()
    }

    override fun onGetPlanListFailed(message: String) {
       showToastError(message)
        activityAddTokentBinding.rvPlan.visibility = View.GONE
        activityAddTokentBinding.tvNoRecord.visibility = View.VISIBLE
    }

    private fun initRecyclerView() {
        planListAdapter =
            PlanListAdapter(this, planList!!, this!!)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        activityAddTokentBinding.rvPlan.layoutManager = linearLayoutManager
        activityAddTokentBinding.rvPlan.adapter = planListAdapter
        activityAddTokentBinding.rvPlan.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

}