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
import com.customvideocalling.databinding.TokenHistoryListItemBinding
import com.customvideocalling.model.JobsResponse
import com.customvideocalling.model.PlanListResponse
import com.customvideocalling.model.TokenHistoryListResponse
import com.customvideocalling.views.MainActivity
import com.customvideocalling.views.VideoChatViewActivity
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.TokenHistoryActivity


class TokeHistoryListAdapter(
        context: TokenHistoryActivity,
        tokenHistoryList: ArrayList<TokenHistoryListResponse.Result>,
        var activity: Context
) :
        RecyclerView.Adapter<TokeHistoryListAdapter.ViewHolder>() {
    private val mContext: TokenHistoryActivity
    private var viewHolder: ViewHolder? = null
    private var tokenHistoryList: ArrayList<TokenHistoryListResponse.Result>

    init {
        this.mContext = context
        this.tokenHistoryList = tokenHistoryList
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.token_history_list_item,
                parent,
                false
        ) as TokenHistoryListItemBinding
        return ViewHolder(binding.root, viewType, binding, mContext, tokenHistoryList)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(@NonNull holder: ViewHolder, position: Int) {
        viewHolder = holder
        holder.binding!!.tvTokenBalanceValue.text = tokenHistoryList!![position].tokenBalance.toString()
        holder.binding!!.tvTokenValue.text = tokenHistoryList!![position].token.toString()
        holder.binding!!.tvDateValue.text = tokenHistoryList!![position].createdAt.toString()
        holder.binding!!.tvTypeValue.text = tokenHistoryList!![position].type.toString()

    }

    override fun getItemCount(): Int {
        return tokenHistoryList.count()
    }

    inner class ViewHolder//This constructor would switch what to findViewBy according to the type of viewType
    (
            v: View, val viewType: Int, //These are the general elements in the RecyclerView
            val binding: TokenHistoryListItemBinding?,
            mContext: TokenHistoryActivity,
            planList: ArrayList<TokenHistoryListResponse.Result>
    ) : RecyclerView.ViewHolder(v) {

    }
}