package com.secretmenu

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.secretmenu.databinding.RowRideBinding
import com.secretmenu.model.RideListModel

class RideListAdapter(context: Activity, arrayList: List<RideListModel.Datum>) : RecyclerView.Adapter<RideListAdapter.MyViewHolder>() {
    private val mContext: Context
    private var arrayList: List<RideListModel.Datum>

    init {
        this.arrayList = arrayList
        this.mContext = context
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_ride, parent, false) as RowRideBinding
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(@NonNull holder: MyViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val datum = arrayList[position]
        holder.binding.myModel = datum
        holder.bind(datum)
        holder.binding.llFirst.setOnClickListener {
            datum.scooterId = datum.scooterId + ".." + position
            Toast.makeText(mContext, "You clicked " + datum.scooterId,
                    Toast.LENGTH_LONG).show()
        }
    }

    fun newData(newArrayList: List<RideListModel.Datum>) {
        arrayList = newArrayList
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    inner class MyViewHolder internal constructor(itemBinding: RowRideBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val binding: RowRideBinding = itemBinding

        fun bind(obj: Any) {
            binding.setVariable(BR.myModel, obj)
            binding.executePendingBindings()
        }
    }
}