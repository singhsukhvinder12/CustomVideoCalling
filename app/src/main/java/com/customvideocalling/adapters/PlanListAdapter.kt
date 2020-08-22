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
import com.customvideocalling.databinding.PlanListItemBinding
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.views.authentication.SignUpActivity
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.PaymentActivity


class PlanListAdapter(
        context: AddTokentActivity,
        planList: ArrayList<PlanListResponse.Result>,
        var activity: Context,
        var throughSignUp: String,
        var callBack: CallBackResult.SelectedPlanCallBack
) :
        RecyclerView.Adapter<PlanListAdapter.ViewHolder>() {
    private val mContext: AddTokentActivity
    private var viewHolder: ViewHolder? = null
    private var planList: ArrayList<PlanListResponse.Result>
    private var mThroughSignUp: String? = null
    private var callBackResult: CallBackResult.SelectedPlanCallBack? = null

    init {
        this.mContext = context
        this.planList = planList
        this.mThroughSignUp = throughSignUp
        this.callBackResult = callBack
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.plan_list_item,
                parent,
                false
        ) as PlanListItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, planList)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.planName.text = planList!![position].description
        holder.binding!!.btSelectPlan.setOnClickListener {
            if (throughSignUp.equals("1")){
                callBackResult!!.selectedPlan(position,planList[position].id.toString(), planList[position].tokenAmount.toString())
            }else{
                val intent = Intent(activity, PaymentActivity::class.java)
                intent.putExtra("amount", planList[position].tokenAmount.toString())
                intent.putExtra("planId", planList[position].id.toString())
                activity.startActivity(intent)
            }

        }
    }

    override fun getItemCount(): Int {
        return planList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
    (
            v: View, val viewType: Int, //These are the general elements in the RecyclerView
            val binding: PlanListItemBinding?,
            mContext: AddTokentActivity,
            planList: ArrayList<PlanListResponse.Result>
    ) : RecyclerView.ViewHolder(v) {

    }
}