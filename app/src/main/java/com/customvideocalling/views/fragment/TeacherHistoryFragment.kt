package com.customvideocalling.views.fragment

import android.app.Dialog
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.R
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.FragmentHomeBinding
import com.customvideocalling.databinding.FragmentTeacherHomeBinding
import com.customvideocalling.model.JobsResponse
import com.customvideocalling.utils.DialogClass
import com.customvideocalling.utils.DialogssInterface
import com.google.gson.JsonObject
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseFragment
import com.customvideocalling.viewmodels.HomeViewModel
import com.customvideocalling.viewmodels.TeacherHomeViewModel
import com.customvideocalling.views.student.AddAppoitmentActivity
import com.uniongoods.adapters.JobRequestsAdapter
import com.uniongoods.adapters.StudentHistoryAdapter
import com.uniongoods.adapters.TeacherHistoryAdapter

class TeacherHistoryFragment : BaseFragment(), DialogssInterface {
    private var pendingJobsList = ArrayList<JobsResponse.Data>()
    private var myTeacherHistoryAdapter : TeacherHistoryAdapter? = null
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    private val mJobListObject = JsonObject()
    private var userId = ""
    override fun getLayoutResId() : Int {
        return R.layout.fragment_teacher_home
    }

    private lateinit var fragmentHomeBinding : FragmentTeacherHomeBinding
    private lateinit var homeViewModel : TeacherHomeViewModel
    private val mJsonObject = JsonObject()
    override fun initView() {
        fragmentHomeBinding = viewDataBinding as FragmentTeacherHomeBinding
        homeViewModel = ViewModelProviders.of(this).get(TeacherHomeViewModel::class.java)
        fragmentHomeBinding.homeViewModel = homeViewModel
        baseActivity.startProgressDialog()
        fragmentHomeBinding.floatAdd.setOnClickListener {
            val intent = Intent(activity, AddAppoitmentActivity::class.java)
            activity!!.startActivity(intent)
        }
        if (UtilsFunctions.isNetworkConnected()) {
             userId =  SharedPrefClass().getPrefValue(activity!!, GlobalConstants.USERID) as String
            homeViewModel.getMyJobsHistory(userId)
        } else {
            baseActivity.stopProgressDialog()
        }

        fragmentHomeBinding.swipeContainer.setOnRefreshListener {
            pendingJobsList.clear()
            homeViewModel.getMyJobsHistory(userId)
        }

        homeViewModel.getJobsHistory().observe(this,
            Observer<JobsResponse> { response->
                baseActivity.stopProgressDialog()
                fragmentHomeBinding.swipeContainer.isRefreshing = false
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            pendingJobsList.addAll(response.data!!)
                            fragmentHomeBinding.rvJobs.visibility = View.VISIBLE
                            fragmentHomeBinding.tvNoRecord.visibility = View.GONE
                            initRecyclerView()
                        }
                        /* response.code == 204 -> {
                             FirebaseFunctions.sendOTP("signup", mJsonObject, this)
                         }*/
                        else -> message?.let {
                        //    UtilsFunctions.showToastError(it)

                            fragmentHomeBinding.rvJobs.visibility = View.GONE
                            fragmentHomeBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                    }

                }
            })


    }


    private fun initRecyclerView() {
        myTeacherHistoryAdapter =
            TeacherHistoryAdapter(this@TeacherHistoryFragment, pendingJobsList, activity!!)
        val linearLayoutManager = LinearLayoutManager(this.baseActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentHomeBinding.rvJobs.layoutManager = linearLayoutManager
        fragmentHomeBinding.rvJobs.adapter = myTeacherHistoryAdapter
        fragmentHomeBinding.rvJobs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView : RecyclerView, dx : Int, dy : Int) {

            }
        })
    }

    override fun onDialogConfirmAction(mView: View?, mKey: String) {
        TODO("Not yet implemented")
    }

    override fun onDialogCancelAction(mView: View?, mKey: String) {
        TODO("Not yet implemented")
    }

    override fun onResume() {
        super.onResume()
        if (UtilsFunctions.isNetworkConnected()) {
            userId =  SharedPrefClass().getPrefValue(activity!!, GlobalConstants.USERID) as String
            pendingJobsList.clear()
            homeViewModel.getMyJobsHistory(userId)
        } else {
            baseActivity.stopProgressDialog()
        }
    }
}