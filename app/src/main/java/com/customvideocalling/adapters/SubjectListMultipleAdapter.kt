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
import com.customvideocalling.model.SubjectListMultipleModel
import com.customvideocalling.model.TimesModel
import com.customvideocalling.views.authentication.TeacherSignupActivity
import com.customvideocalling.views.teacher.AddScheduleActivity


class SubjectListMultipleAdapter(var context: TeacherSignupActivity, var subjectList: ArrayList<SubjectListMultipleModel>) : RecyclerView.Adapter<SubjectListMultipleAdapter.ViewHolder>() {

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

        holder.binding!!.tvTitle.text = subjectList.get(position).name.toString()
        if (subjectList.get(position).isSelected.equals("false")){
            holder.binding.icSelect.setImageResource(R.drawable.ic_unchecked)
        }else{
            holder.binding.icSelect.setImageResource(R.drawable.ic_checked)

        }

        holder.binding.parentClick.setOnClickListener {

            if (subjectList.get(position).isSelected.equals("true")){
                subjectList.get(position).isSelected="false"
            }else   if (subjectList.get(position).isSelected.equals("false")){

                subjectList.get(position).isSelected="true"

            }
            notifyDataSetChanged()

            context.updateValueForSubject()

        }
    }

    override fun getItemCount(): Int {
        return subjectList.size
    }

    inner class ViewHolder
        (
        v: View, val viewType: Int,
        val binding: DaysDropdownLayoutBinding?,
        mContext: TeacherSignupActivity
    ) : RecyclerView.ViewHolder(v)
}