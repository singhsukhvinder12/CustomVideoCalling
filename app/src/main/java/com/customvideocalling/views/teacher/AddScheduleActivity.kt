package com.customvideocalling.views.teacher

import android.app.DatePickerDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.adapters.TimeDropdownAdapter
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityAddScheduleBinding
import com.customvideocalling.model.DaysModel
import com.customvideocalling.model.TimesModel
import com.customvideocalling.utils.DateTimeUtil
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.AddScheduleViewModel
import com.customvideocalling.viewmodels.TeacherNotificationViewModel
import com.example.artha.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.uniongoods.adapters.DaysListAdapter
import com.uniongoods.adapters.TimeListAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddScheduleActivity : BaseActivity(), View.OnClickListener, CallBackResult.AddScheduleCallBack {

    var binding:ActivityAddScheduleBinding?=null
    private lateinit var addScheduleViewModel: AddScheduleViewModel
    private var sharedPrefClass = SharedPrefClass()
    var daysList:ArrayList<DaysModel>?=null
    var timeList:ArrayList<TimesModel>?=null
   lateinit var pw:PopupWindow
   lateinit var pw2:PopupWindow
    private var startDate = ""
    private var endDate = ""
    private var daysListFinal: ArrayList<DaysModel>?=null
    private var timeListFinal: ArrayList<TimesModel>?=null

    override fun initViews() {
        binding=viewDataBinding as ActivityAddScheduleBinding
        addScheduleViewModel = ViewModelProviders.of(this).get(AddScheduleViewModel::class.java)
        binding!!.commonToolBar.toolbarText.setText("Add Schedule")
        binding!!.commonToolBar.toolbarBack.setOnClickListener(this)
        daysList= ArrayList<DaysModel>()

        daysList!!.add(DaysModel("Sunday","sun","false"))
        daysList!!.add(DaysModel("Monday","mon","false"))
        daysList!!.add(DaysModel("Tuesday","tue","false"))
        daysList!!.add(DaysModel("Wednesday","wed","false"))
        daysList!!.add(DaysModel("Thursday","thu","false"))
        daysList!!.add(DaysModel("Friday","fri","false"))
        daysList!!.add(DaysModel("Saturday","sat","false"))

        timeList= ArrayList<TimesModel>()
        timeList!!.add(TimesModel("07:00 AM","false"))
        timeList!!.add(TimesModel("08:00 AM","false"))
        timeList!!.add(TimesModel("09:00 AM","false"))
        timeList!!.add(TimesModel("10:00 AM","false"))
        timeList!!.add(TimesModel("11:00 AM","false"))
        timeList!!.add(TimesModel("12:00 PM","false"))
        timeList!!.add(TimesModel("01:00 PM","false"))
        timeList!!.add(TimesModel("02:00 PM","false"))
        timeList!!.add(TimesModel("03:00 PM","false"))
        timeList!!.add(TimesModel("04:00 PM","false"))
        timeList!!.add(TimesModel("05:00 PM","false"))
        timeList!!.add(TimesModel("06:00 PM","false"))
        timeList!!.add(TimesModel("07:00 PM","false"))
        timeList!!.add(TimesModel("08:00 PM","false"))



        binding!!.relSpinnerDays.setOnClickListener(this)
        binding!!.relSpinnerTime.setOnClickListener(this)
        binding!!.tvStartDate.setOnClickListener(this)
        binding!!.tvEndDate.setOnClickListener(this)
        binding!!.btnAddSchedule.setOnClickListener(this)




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
        daysListFinal = ArrayList()
        for ( i in 0 until daysList!!.size){

            if (daysList!!.get(i).isSelected.equals("true")){
                daysListFinal!!.add(daysList!!.get(i))
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
        timeListFinal = ArrayList()
        for ( i in 0 until timeList!!.size){

            if (timeList!!.get(i).isSelected.equals("true")){
                timeListFinal!!.add(timeList!!.get(i))
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
                R.id.toolbar_back -> {
                    finish()
                }
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
            R.id.btn_add_schedule ->{
                if (daysListFinal == null ||daysListFinal!!.isEmpty()){
                    showToastError("Please select days")
                }
                else if(timeListFinal == null || timeListFinal!!.isEmpty()){
                    showToastError("Please select slot time")
                }else if(startDate.isEmpty()){
                    showToastError("Please select start date")
                }else if (endDate.isEmpty()){
                    showToastError("Please select end date")
                }
                else {
                    startProgressDialog()
                    val gson = GsonBuilder().setPrettyPrinting().create()
                    val array = JsonArray()
                    for (i in 0 until daysListFinal!!.size) {
                        array.add(daysListFinal!![i].apiName)
                    }
                    val gsonTime = GsonBuilder().setPrettyPrinting().create()
                    val arrayTime = JsonArray()
                    for (i in 0 until timeListFinal!!.size) {
                        arrayTime.add(timeListFinal!![i].name)
                    }
                    val mJsonObject = JsonObject()
                    mJsonObject.addProperty(
                        "teacherId",
                        sharedPrefClass.getPrefValue(this, GlobalConstants.USERID).toString()
                    )
                    mJsonObject.addProperty("slotsTime", gsonTime.toJson(arrayTime))
                    mJsonObject.addProperty("slotsday", gson.toJson(array))
                    mJsonObject.addProperty("fromDate", startDate)
                    mJsonObject.addProperty("toDate", endDate)
                    addScheduleViewModel.hitAddSchedule(this, mJsonObject)
                }
            }
        }
    }

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
                startDate = sdf2.format(calendar.time)
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
                endDate = sdf2.format(calendar.time)
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

    override fun onAddScheduleSuccess(response: CommonModel) {
        stopProgressDialog()
        finish()
    }

    override fun onAddScheduleFailed(message: String) {
        showToastError(message)
    }

}