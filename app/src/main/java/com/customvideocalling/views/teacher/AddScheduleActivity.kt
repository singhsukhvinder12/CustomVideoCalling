package com.customvideocalling.views.teacher

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.R
import com.customvideocalling.adapters.TimeDropdownAdapter
import com.customvideocalling.databinding.ActivityAddScheduleBinding
import com.customvideocalling.model.DaysModel
import com.customvideocalling.model.TimesModel
import com.customvideocalling.utils.DateTimeUtil
import com.customvideocalling.utils.core.BaseActivity
import com.google.gson.JsonObject
import com.uniongoods.adapters.DaysListAdapter
import com.uniongoods.adapters.TimeListAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddScheduleActivity : BaseActivity(), View.OnClickListener {

    var binding:ActivityAddScheduleBinding?=null
    var daysList:ArrayList<DaysModel>?=null
    var timeList:ArrayList<TimesModel>?=null
   lateinit var pw:PopupWindow
   lateinit var pw2:PopupWindow

    override fun initViews() {
        binding=viewDataBinding as ActivityAddScheduleBinding
        daysList= ArrayList<DaysModel>()

        daysList!!.add(DaysModel("Sunday","false"))
        daysList!!.add(DaysModel("Monday","false"))
        daysList!!.add(DaysModel("Tuesday","false"))
        daysList!!.add(DaysModel("Wednesday","false"))
        daysList!!.add(DaysModel("Thusday","false"))
        daysList!!.add(DaysModel("Friday","false"))
        daysList!!.add(DaysModel("Saturday","false"))

        timeList= ArrayList<TimesModel>()
        timeList!!.add(TimesModel("7:00AM","false"))
        timeList!!.add(TimesModel("7:00AM","false"))
        timeList!!.add(TimesModel("7:00AM","false"))
        timeList!!.add(TimesModel("7:00AM","false"))
        timeList!!.add(TimesModel("7:00AM","false"))



        binding!!.relSpinnerDays.setOnClickListener(this)
        binding!!.relSpinnerTime.setOnClickListener(this)
        binding!!.tvStartDate.setOnClickListener(this)
        binding!!.tvEndDate.setOnClickListener(this)




        var inflater = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var views=inflater!!.inflate(R.layout.days_dialog, null, false)
        pw =  PopupWindow(views, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        pw!!.setOutsideTouchable(true);
        var daysRecyclerView=views.findViewById<RecyclerView>(R.id.rvDays)


        val adapter = DaysListAdapter(this, daysList!!)
        var layout=LinearLayoutManager(this)
        daysRecyclerView.adapter=adapter
        daysRecyclerView.layoutManager=layout



        //time popup

        var inflater2 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var views2=inflater2!!.inflate(R.layout.days_dialog, null, false)
        pw2 =  PopupWindow(views2, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        pw2!!.setOutsideTouchable(true);
        var daysRecyclerView2=views2.findViewById<RecyclerView>(R.id.rvDays)


        val adapter2 = TimeListAdapter(this, timeList!!)
        var layout2=LinearLayoutManager(this)
        daysRecyclerView2.adapter=adapter2
        daysRecyclerView2.layoutManager=layout2





    }

    fun updateValue(){

        var multipleValue=""
        for ( i in 0 until daysList!!.size){

            if (daysList!!.get(i).isSelected.equals("true")){
                if (multipleValue.isEmpty()){
                    multipleValue=daysList!!.get(i).name
                }else{
                    multipleValue=multipleValue+","+daysList!!.get(i).name

                }

            }

        }


        if (multipleValue.isEmpty()){
            binding!!.tvDays.text="Select day"
        }else{
            binding!!.tvDays.text=multipleValue

        }
    }
    fun updateValueForTime(){

        var multipleValue=""
        for ( i in 0 until timeList!!.size){

            if (timeList!!.get(i).isSelected.equals("true")){
                if (multipleValue.isEmpty()){
                    multipleValue=timeList!!.get(i).name
                }else{
                    multipleValue=multipleValue+","+timeList!!.get(i).name

                }

            }

        }

        if (multipleValue.isEmpty()){
            binding!!.tvTime.text="Select time"
        }else{
            binding!!.tvTime.text=multipleValue

        }

     }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_schedule
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.rel_spinner_days->{
                pw!!.showAsDropDown(binding!!.relSpinnerDays)
            }

            R.id.rel_spinner_time->{
                pw2!!.showAsDropDown(binding!!.relSpinnerTime)
            }
            R.id.tvStartDate->{
                selectDatePicker()
            }
            R.id.tvEndDate->{
                endDateDatePicker()
            }
        }
    }
    private var date = ""
    fun selectDatePicker() {
        val calendar = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd/MM/yyyy"
            val myFormat2 = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val sdf2 = SimpleDateFormat(myFormat2, Locale.US)
            if (DateTimeUtil.checkPastDay(calendar2, calendar)
            ) {
                showToastError("Please select future date")
            } else {
                //strDate = sdf.format(calendar.time)
                date = sdf2.format(calendar.time)
                binding!!.tvStartDate.setText(sdf.format(calendar.time))
                val mJsonObject = JsonObject()
//                mJsonObject.addProperty("subjectId", subjectId)
//                mJsonObject.addProperty("date", date)
//                bookingAddStudentViewModel.getSlotList(this, mJsonObject)
            }
        }
        val dpDialog = DatePickerDialog(
            this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpDialog.show()
    }

    fun endDateDatePicker() {
        val calendar = Calendar.getInstance()
        val calendar2 = Calendar.getInstance()
        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            val myFormat = "dd/MM/yyyy"
            val myFormat2 = "yyyy-MM-dd"
            val sdf = SimpleDateFormat(myFormat, Locale.US)
            val sdf2 = SimpleDateFormat(myFormat2, Locale.US)
            if (DateTimeUtil.checkPastDay(calendar2, calendar)
            ) {
                showToastError("Please select future date")
            } else {
                //strDate = sdf.format(calendar.time)
                date = sdf2.format(calendar.time)
                binding!!.tvEndDate.setText(sdf.format(calendar.time))
                val mJsonObject = JsonObject()
//                mJsonObject.addProperty("subjectId", subjectId)
//                mJsonObject.addProperty("date", date)
//                bookingAddStudentViewModel.getSlotList(this, mJsonObject)
            }
        }
        val dpDialog = DatePickerDialog(
            this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpDialog.show()
    }

}