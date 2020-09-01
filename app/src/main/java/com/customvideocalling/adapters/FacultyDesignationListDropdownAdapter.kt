package com.customvideocalling.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.customvideocalling.R
import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.facultyDesignationResponse


class FacultyDesignationListDropdownAdapter(val context: Context, var dropDownList: ArrayList<facultyDesignationResponse.facultyItem>?) :
        BaseAdapter() {


    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.dropdown_layout, parent, false)
            vh = ItemRowHolder(view)
            //Toast.makeText(context,dropDownList!![position].Name.toString(),1000).show()
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }
        /* if (position==0){
             vh.label.text ="Please select Class"
         }
         else{*/
        vh.label.text = dropDownList!![position].name
        //}

        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {
        return dropDownList!!.size
    }

    private class ItemRowHolder(row: View?) {

        val label: TextView

        init {
            this.label = row?.findViewById(R.id.tv_title) as TextView
        }
    }
}