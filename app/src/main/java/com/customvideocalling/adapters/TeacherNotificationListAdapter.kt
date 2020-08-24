package com.uniongoods.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.databinding.PlanListItemBinding
import com.customvideocalling.databinding.RequestItemBinding
import com.customvideocalling.databinding.TeacherNotificationListItemBinding
import com.customvideocalling.databinding.TokenHistoryListItemBinding
import com.customvideocalling.model.JobsResponse
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.model.TeacherNotificationListResponse
import com.customvideocalling.model.TokenHistoryListResponse
import com.customvideocalling.views.MainActivity
import com.customvideocalling.views.VideoChatViewActivity
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.TokenHistoryActivity
import com.customvideocalling.views.teacher.TeacherNotificationListActivity


class TeacherNotificationListAdapter(
    context: TeacherNotificationListActivity,
    tokenHistoryList: ArrayList<TeacherNotificationListResponse.Result>,
    var activity: Context,
    var callBackResult: CallBackResult.TeacherAcceptRejectCallBack
) :
        RecyclerView.Adapter<TeacherNotificationListAdapter.ViewHolder>() {
    private val mContext: TeacherNotificationListActivity
    private var viewHolder: ViewHolder? = null
    private var tokenHistoryList: ArrayList<TeacherNotificationListResponse.Result>
    private var callBack: CallBackResult.TeacherAcceptRejectCallBack?=null

    init {
        this.mContext = context
        this.tokenHistoryList = tokenHistoryList
        this.callBack = callBackResult
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.teacher_notification_list_item,
                parent,
                false
        ) as TeacherNotificationListItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, tokenHistoryList)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.tvStudentNameValue.text = tokenHistoryList!![position].bookingDetail!!.userDetail!!.firstName+" "+
                tokenHistoryList!![position].bookingDetail!!.userDetail!!.lastName
        holder.binding!!.tvTimeValue.text = tokenHistoryList!![position].bookingDetail!!.timeSlot
        holder.binding!!.tvDateValue.text = tokenHistoryList!![position].bookingDetail!!.bookingDate
        if (tokenHistoryList!![position].status==0){
         holder.binding!!.linLayButtons.visibility = View.VISIBLE
        }else{
            holder.binding!!.linLayButtons.visibility = View.GONE
        }
        holder.binding!!.btnAccept.setOnClickListener {
            callBack!!.onClickAcceptReject(position, 1)
        }
        holder.binding!!.btnReject.setOnClickListener {
            callBack!!.onClickAcceptReject(position, 2)
        }

    }

    override fun getItemCount(): Int {
        return tokenHistoryList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
    (
            v: View, val viewType: Int, //These are the general elements in the RecyclerView
            val binding: TeacherNotificationListItemBinding?,
            mContext: TeacherNotificationListActivity,
            planList: ArrayList<TeacherNotificationListResponse.Result>
    ) : RecyclerView.ViewHolder(v) {

    }
}