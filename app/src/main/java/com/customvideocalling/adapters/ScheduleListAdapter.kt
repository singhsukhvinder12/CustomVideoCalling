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
import com.customvideocalling.R
import com.customvideocalling.databinding.PlanListItemBinding
import com.customvideocalling.databinding.RequestItemBinding
import com.customvideocalling.databinding.ScheduleListItemBinding
import com.customvideocalling.databinding.TokenHistoryListItemBinding
import com.customvideocalling.model.JobsResponse
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.model.ScheduleListResponse
import com.customvideocalling.model.TokenHistoryListResponse
import com.customvideocalling.views.MainActivity
import com.customvideocalling.views.TeacherMainActivity
import com.customvideocalling.views.VideoChatViewActivity
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.TokenHistoryActivity
import com.customvideocalling.views.teacher.ScheduleDetailActivity
import com.customvideocalling.views.teacher.ScheduleListActivity


class ScheduleListAdapter(
        context: ScheduleListActivity,
        scheduleList: ArrayList<ScheduleListResponse.Result>,
        var activity: Context
) :
        RecyclerView.Adapter<ScheduleListAdapter.ViewHolder>() {
    private val mContext: ScheduleListActivity
    private var viewHolder: ViewHolder? = null
    private var scheduleList: ArrayList<ScheduleListResponse.Result>

    init {
        this.mContext = context
        this.scheduleList = scheduleList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.schedule_list_item,
                parent,
                false
        ) as ScheduleListItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, scheduleList)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.tvFromDateValue.text = scheduleList!![position].fromDate.toString()
        holder.binding!!.tvToDateValue.text = scheduleList!![position].toDate.toString()
        holder.binding!!.cardView.setOnClickListener {
            val intent = Intent(mContext, ScheduleDetailActivity::class.java)
            mContext.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return scheduleList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
    (
            v: View, val viewType: Int, //These are the general elements in the RecyclerView
            val binding: ScheduleListItemBinding?,
            mContext: ScheduleListActivity,
            planList: ArrayList<ScheduleListResponse.Result>
    ) : RecyclerView.ViewHolder(v) {

    }
}