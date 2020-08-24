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
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityAddAppoitmentBinding
import com.customvideocalling.databinding.ActivityAddTokentBinding
import com.customvideocalling.databinding.ActivityTokenHistoryBinding
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.model.TokenHistoryListResponse
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.AddTokenViewModel
import com.customvideocalling.viewmodels.BookingAddStudentViewModel
import com.customvideocalling.viewmodels.TokenHistoryViewModel
import com.uniongoods.adapters.JobRequestsAdapter
import com.uniongoods.adapters.PlanListAdapter
import com.uniongoods.adapters.TokeHistoryListAdapter

class TokenHistoryActivity : BaseActivity(), View.OnClickListener, CallBackResult.TokenHistoryCallBack {
    private lateinit var activityTokenHistoryBinding: ActivityTokenHistoryBinding
    private lateinit var tokenHistoryViewModel: TokenHistoryViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var tokenHistoryList: ArrayList<TokenHistoryListResponse.Result>?=null
    private var tokenHistoryListAdapter : TokeHistoryListAdapter? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_token_history
    }

    override fun initViews() {
        activityTokenHistoryBinding = DataBindingUtil.setContentView(this, R.layout.activity_token_history)
        tokenHistoryViewModel = ViewModelProviders.of(this).get(TokenHistoryViewModel::class.java)
        activityTokenHistoryBinding.toolBar.toolbarText.setText(getString(R.string.token_history))
        activityTokenHistoryBinding.toolBar.toolbarBack.setOnClickListener(this)
        tokenHistoryList = ArrayList()
        if (sharedPrefClass.getPrefValue(this,GlobalConstants.TYPE) == "1") {
            tokenHistoryViewModel.getTokenHistoryList(
                this,
                sharedPrefClass.getPrefValue(this, GlobalConstants.USERID).toString()
            )
        }else {
            tokenHistoryViewModel.getTeacherTokenHistoryList(
                this,
                sharedPrefClass.getPrefValue(this, GlobalConstants.USERID).toString()
            )
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.toolbar_back -> {
                finish()
            }
        }
    }

    private fun initRecyclerView() {
        tokenHistoryListAdapter =
            TokeHistoryListAdapter(this, tokenHistoryList!!, this!!)
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        activityTokenHistoryBinding.rvTokenHistory.layoutManager = linearLayoutManager
        activityTokenHistoryBinding.rvTokenHistory.adapter = tokenHistoryListAdapter
        activityTokenHistoryBinding.rvTokenHistory.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    override fun onGetTokenHistorySuccess(response: TokenHistoryListResponse) {
        tokenHistoryList = response.result!!
        if (tokenHistoryList != null && tokenHistoryList!!.size>0) {
            activityTokenHistoryBinding.rvTokenHistory.visibility = View.VISIBLE
            activityTokenHistoryBinding.tvNoRecord.visibility = View.GONE
        }else{
            activityTokenHistoryBinding.rvTokenHistory.visibility = View.GONE
            activityTokenHistoryBinding.tvNoRecord.visibility = View.VISIBLE
        }
        initRecyclerView()
    }

    override fun onGetTokenHistoryFailed(message: String) {
        showToastError(message)
        activityTokenHistoryBinding.rvTokenHistory.visibility = View.GONE
        activityTokenHistoryBinding.tvNoRecord.visibility = View.VISIBLE
    }

}