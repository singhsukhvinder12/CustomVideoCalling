package com.secretmenu

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater.from
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.secretmenu.databinding.RowUserBinding
import com.secretmenu.roomdatabase.User

class RoomListAdapter(context: Activity, arrayList: List<User>) : RecyclerView.Adapter<RoomListAdapter.MyViewHolder>() {
    private val mContext: Context
    private var arrayList: List<User>

    init {
        this.arrayList = arrayList
        this.mContext = context
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = DataBindingUtil.inflate(from(parent.context), R.layout.row_user, parent, false) as RowUserBinding
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(@NonNull holder: MyViewHolder, position: Int) {
        val datum = arrayList[position]
        holder.binding.myModel = datum
        holder.bind(datum)

    }

    fun newData(newArrayList: List<User>) {
        arrayList = newArrayList
    }

    override fun getItemCount(): Int {
        return arrayList.size

    }

    inner class MyViewHolder internal constructor(itemBinding: RowUserBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        val binding: RowUserBinding = itemBinding

        fun bind(obj: Any) {
            binding.setVariable(BR.myModel, obj)
            binding.executePendingBindings()
        }
    }
}