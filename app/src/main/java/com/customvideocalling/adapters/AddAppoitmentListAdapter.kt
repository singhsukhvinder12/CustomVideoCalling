package com.uniongoods.adapters

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.databinding.AppoitmentListItemBinding
import com.customvideocalling.databinding.PlanListItemBinding
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.views.authentication.SignUpActivity
import com.customvideocalling.views.student.AddAppoitmentActivity
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.PaymentActivity


class AddAppoitmentListAdapter(
        context: AddAppoitmentActivity,
        appoitmentPlanList: ArrayList<String>,
        var activity: Context
) :
        RecyclerView.Adapter<AddAppoitmentListAdapter.ViewHolder>() {
    private val mContext: AddAppoitmentActivity
    private var viewHolder: ViewHolder? = null
    private var planList: ArrayList<String>

    init {
        this.mContext = context
        this.planList = appoitmentPlanList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.plan_list_item,
                parent,
                false
        ) as AppoitmentListItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, planList)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
       if (position == 0){
         holder.binding!!.imageAppoitmentPlan.setImageResource(R.drawable.how_woks_1)
       }else if (position == 1){
           holder.binding!!.imageAppoitmentPlan.setImageResource(R.drawable.how_works_2)
       }else if(position == 2){
           holder.binding!!.imageAppoitmentPlan.setImageResource(R.drawable.how_works_3)
       }
        holder.binding!!.tvAppoitmentPlan.text = planList[position]
        holder.binding!!.maiLayout.setOnClickListener {
            mContext.onClickPlan(position)
        }
    }

    override fun getItemCount(): Int {
        return planList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
    (
            v: View, val viewType: Int, //These are the general elements in the RecyclerView
            val binding: AppoitmentListItemBinding?,
            mContext: AddAppoitmentActivity,
            planList: ArrayList<String>
    ) : RecyclerView.ViewHolder(v) {

    }
}