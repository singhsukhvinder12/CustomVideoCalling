package com.customvideocalling.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.customvideocalling.R;
import com.customvideocalling.model.ScheduleDetailResponse;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Objects;

import ayalma.ir.expandablerecyclerview.ExpandableRecyclerView;

public class ScheduleDetailExpandableAdapter extends ExpandableRecyclerView.Adapter<ScheduleDetailExpandableAdapter.ChildViewHolder,ExpandableRecyclerView.SimpleGroupViewHolder,String,String>
{
private ArrayList<ScheduleDetailResponse.Result> mScheduleDetailList;

    public ScheduleDetailExpandableAdapter(@Nullable ArrayList<ScheduleDetailResponse.Result> scheduleDetailParent) {
        this.mScheduleDetailList = scheduleDetailParent;
    }

    @Override
    public int getGroupItemCount() {
        return this.mScheduleDetailList.size()-1;
    }

    @Override
    public int getChildItemCount(int i) {
        return Objects.requireNonNull(mScheduleDetailList.get(i).getSlotsList()).size();
    }

    @Override
    public String getGroupItem(int i) {
        return mScheduleDetailList.get(i).getName();
    }

    @Override
    public String getChildItem(int group, int child) {
        return Objects.requireNonNull(mScheduleDetailList.get(group).getSlotsList()).get(child).getSlot();
    }

    @Override
    protected ExpandableRecyclerView.SimpleGroupViewHolder onCreateGroupViewHolder(ViewGroup parent)
    {
        return new ExpandableRecyclerView.SimpleGroupViewHolder(parent.getContext());
    }

    @Override
    protected ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType)
    {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.schedule_detail_item,parent,false);
        return new ChildViewHolder(rootView);
    }

    @Override
    public void onBindGroupViewHolder(ExpandableRecyclerView.SimpleGroupViewHolder holder, int group) {
        super.onBindGroupViewHolder(holder, group);
            holder.setText(getGroupItem(group));
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int group, int position)
    {
        super.onBindChildViewHolder(holder, group, position);
        holder.name.setText(getChildItem(group,position));
    }

    @Override
    public int getChildItemViewType(int i, int i1) {
        return 1;
    }

    public class ChildViewHolder extends RecyclerView.ViewHolder
    {
        private TextView name;
        public ChildViewHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.item_name);
        }
    }
    public void setData(ArrayList<ScheduleDetailResponse.Result> scheduleDetailParent){
        mScheduleDetailList = scheduleDetailParent;
        notifyDataSetChanged();
    }
}
