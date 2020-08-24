package com.uniongoods.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.R
import com.customvideocalling.databinding.RequestItemBinding
import com.customvideocalling.model.JobsResponse
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.fragment.StudentHistoryFragment
import com.customvideocalling.views.fragment.TeacherHistoryFragment


class TeacherHistoryAdapter(
        context: TeacherHistoryFragment,
        jobsList: ArrayList<JobsResponse.Data>,
        var activity: Context
) :
        RecyclerView.Adapter<TeacherHistoryAdapter.ViewHolder>() {
    private val mContext: TeacherHistoryFragment
    private var viewHolder: ViewHolder? = null
    private var jobsList: ArrayList<JobsResponse.Data>

    init {
        this.mContext = context
        this.jobsList = jobsList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.request_item,
                parent,
                false
        ) as RequestItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, jobsList)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        //holder.binding!!.tvAddress.text = jobsList!![position]
        holder.binding!!.tvTime.text = jobsList[position].timeSlot
        holder.binding!!.tvFromLocationName.text = jobsList[position].bookingDate
      //  holder.binding.tvToLocationName.text = jobsList[position].to_location

      /*  if (!TextUtils.isEmpty(jobsList[position].scheduleDatetime) && !jobsList[position].scheduleDatetime.equals("null")) {
            holder.binding.tvTimeValue.text = Utils(activity).getDate(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
                    jobsList[position].scheduleDatetime,
                    "dd-MMM,yyyy | hh:mm a"
            )
        }
        holder.binding.tvTypeValue.text = jobsList[position].jobType?.jobType
        if (jobsList[position].jobType?.jobType == "Taxi") {
            holder.binding.tvLoad.text = mContext.getString(R.string.persons)
            holder.binding.tvLoadValue.text = jobsList[position].passengers
        } else {
            holder.binding.tvLoad.text = mContext.getString(R.string.load)
            holder.binding.tvLoadValue.text =
                    jobsList[position].loadTones + " " + mContext.getString(R.string.tons)
        }*/

       /* holder.binding.btnAccept.setOnClickListener {
            mContext.jobAccpetReject(jobsList[position].id, "1")
        }
        holder.binding.btnReject.setOnClickListener {
            mContext.jobAccpetReject(jobsList[position].id, "2")
        }*/
    }

    override fun getItemCount(): Int {
        return jobsList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
    (
            v: View, val viewType: Int, //These are the general elements in the RecyclerView
            val binding: RequestItemBinding?,
            mContext: TeacherHistoryFragment,
            jobsList: ArrayList<JobsResponse.Data>
    ) : RecyclerView.ViewHolder(v) {

    }
}