package com.uniongoods.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.R
import com.customvideocalling.databinding.DaysDropdownLayoutBinding
import com.customvideocalling.model.DaysModel
import com.customvideocalling.views.teacher.AddScheduleActivity


class DaysListAdapter(var context: AddScheduleActivity, var daysList: ArrayList<DaysModel>) : RecyclerView.Adapter<DaysListAdapter.ViewHolder>() {

    init {

    }
    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.days_dropdown_layout,
            parent,
            false
        ) as DaysDropdownLayoutBinding
        return ViewHolder(binding.root, viewType, binding, context)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {

        holder.binding!!.tvTitle.text = daysList.get(position).name.toString()
        if (daysList.get(position).isSelected.equals("false")){
            holder.binding.icSelect.setImageResource(R.drawable.ic_unchecked)
        }else{
            holder.binding.icSelect.setImageResource(R.drawable.ic_checked)

        }

        holder.binding.parentClick.setOnClickListener {

            if (daysList.get(position).isSelected.equals("true")){
                daysList.get(position).isSelected="false"
            }else   if (daysList.get(position).isSelected.equals("false")){

                daysList.get(position).isSelected="true"

            }
            notifyDataSetChanged()

            context.updateValue()

        }
    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    inner class ViewHolder
        (
        v: View, val viewType: Int,
        val binding: DaysDropdownLayoutBinding?,
        mContext: AddScheduleActivity
    ) : RecyclerView.ViewHolder(v)
}