package com.customvideocalling.views.fragment

import android.app.Dialog
import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.customvideocalling.R
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.FragmentTeacherHomeBinding
import com.customvideocalling.model.JobsResponse
import com.customvideocalling.utils.DialogClass
import com.customvideocalling.utils.DialogssInterface
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseFragment
import com.customvideocalling.viewmodels.TeacherHomeViewModel
import com.customvideocalling.views.student.AddAppoitmentActivity
import com.google.gson.JsonObject
import com.uniongoods.adapters.TeacherLiveAdapter


class TeacherLiveFragment : BaseFragment(), DialogssInterface {
    private var pendingJobsList = ArrayList<JobsResponse.Data>()
    private var mTeacherLiveAdapter : TeacherLiveAdapter? = null
    private var confirmationDialog : Dialog? = null
    private var mDialogClass = DialogClass()
    private val mJobListObject = JsonObject()
    private var userId = ""
    override fun getLayoutResId() : Int {
        return R.layout.fragment_teacher_home
    }

    private lateinit var fragmentTeacherHomeBinding : FragmentTeacherHomeBinding
    private lateinit var teacherHomeViewModel : TeacherHomeViewModel
    private val mJsonObject = JsonObject()
    override fun initView() {
        fragmentTeacherHomeBinding = viewDataBinding as FragmentTeacherHomeBinding
        teacherHomeViewModel = ViewModelProviders.of(this).get(TeacherHomeViewModel::class.java)
        fragmentTeacherHomeBinding.homeViewModel = teacherHomeViewModel
        baseActivity.startProgressDialog()
        fragmentTeacherHomeBinding.floatAdd.setOnClickListener {
            val intent = Intent(activity, AddAppoitmentActivity::class.java)
            activity!!.startActivity(intent)
        }

        if (UtilsFunctions.isNetworkConnected()) {
            userId =  SharedPrefClass().getPrefValue(activity!!, GlobalConstants.USERID) as String
            teacherHomeViewModel.getMyJobs(userId)
        } else {
            baseActivity.stopProgressDialog()
        }

        fragmentTeacherHomeBinding.swipeContainer.setOnRefreshListener {
            pendingJobsList.clear()
            teacherHomeViewModel.getMyJobs(userId)
        }


        // Your code to refresh the list here.
            // Make sure you call swipeContainer.setRefreshing(false)
            // once the network request has completed successfully.


        teacherHomeViewModel.getJobs().observe(this,
            Observer<JobsResponse> { response->
                baseActivity.stopProgressDialog()
                fragmentTeacherHomeBinding.swipeContainer.isRefreshing = false
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {
                            pendingJobsList.addAll(response.data!!)
                            fragmentTeacherHomeBinding.rvJobs.visibility = View.VISIBLE
                            fragmentTeacherHomeBinding.tvNoRecord.visibility = View.GONE
                            initRecyclerView()
                        }
                        /* response.code == 204 -> {
                             FirebaseFunctions.sendOTP("signup", mJsonObject, this)
                         }*/
                        else -> message?.let {
                          //  UtilsFunctions.showToastError(it)

                            fragmentTeacherHomeBinding.rvJobs.visibility = View.GONE
                            fragmentTeacherHomeBinding.tvNoRecord.visibility = View.VISIBLE
                        }
                    }

                }
            })


    }


    private fun initRecyclerView() {
        mTeacherLiveAdapter =
            TeacherLiveAdapter(this@TeacherLiveFragment, pendingJobsList, activity!!)
        val linearLayoutManager = LinearLayoutManager(this.baseActivity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        fragmentTeacherHomeBinding.rvJobs.layoutManager = linearLayoutManager
        fragmentTeacherHomeBinding.rvJobs.adapter = mTeacherLiveAdapter
        fragmentTeacherHomeBinding.rvJobs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
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
            teacherHomeViewModel.getMyJobs(userId)

        } else {
            baseActivity.stopProgressDialog()
        }
    }
}