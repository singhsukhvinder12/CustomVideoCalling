package com.customvideocalling.views.student

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.adapters.SlotListDropdownAdapter
import com.customvideocalling.adapters.SubjectListDropdownAdapter
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityAddAppoitmentBinding
import com.customvideocalling.databinding.ActivityLoginBinding
import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.SlotListResponse
import com.customvideocalling.utils.DateTimeUtil
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.BookingAddStudentViewModel
import com.customvideocalling.viewmodels.LoginViewModel
import com.example.artha.model.CommonModel
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddAppoitmentActivity : BaseActivity(), CallBackResult.ClassSubjectListCallBack,
View.OnClickListener, CallBackResult.AddBookingCallBack, CallBackResult.SlotListCallBack{
    private lateinit var activityAddAppoitmentBinding: ActivityAddAppoitmentBinding
    private lateinit var bookingAddStudentViewModel: BookingAddStudentViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var subjectList: ArrayList<ClassSubjectListResponse.Subject>?=null
    private var slotList: ArrayList<String>? = null
    private var selectedSlot = ""
    private var subjectId = ""
    private var date = ""

    override fun initViews() {
        activityAddAppoitmentBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_appoitment)
        bookingAddStudentViewModel = ViewModelProviders.of(this).get(BookingAddStudentViewModel::class.java)
        activityAddAppoitmentBinding.toolBar.toolbarText.setText(getString(R.string.add_booking))
        activityAddAppoitmentBinding.toolBar.toolbarBack.setOnClickListener(this)
        activityAddAppoitmentBinding.btnAddBooking.setOnClickListener(this)
        activityAddAppoitmentBinding.tvDate.setOnClickListener(this)
        subjectList = ArrayList()
        slotList = ArrayList()
        bookingAddStudentViewModel.getSubjectList(this)
        subjectItemSelection()
        slotItemSelection()
        slotList!!.add(0,"Please Select")
        val adapter = SlotListDropdownAdapter(this, slotList)
        activityAddAppoitmentBinding!!.spSlot.adapter = adapter
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_add_appoitment
    }

    override fun onGetClassSubjectListSuccess(response: ClassSubjectListResponse) {
        subjectList!!.clear()
        val subject = ClassSubjectListResponse.Subject()
        subject.id = "0"
        subject.name = "Please Select"
        subjectList!!.add(0,subject)
        if (response.result!!.subjectList!=null && !response.result!!.subjectList!!.isEmpty()) {
            subjectList!!.addAll(response.result!!.subjectList!!)
        }
        val adapter = SubjectListDropdownAdapter(this, subjectList)
        activityAddAppoitmentBinding!!.spSubject.adapter = adapter
    }

    override fun onGetClassSubjectListFailed(message: String) {
      showToastError(message)
    }

    private fun subjectItemSelection() {
        activityAddAppoitmentBinding!!.spSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if (subjectList != null) {
                    subjectId = subjectList!![p2].id!!
                    //   binding!!.noRecordFound.visibility = View.GONE
                }
            }
        }
    }

    private fun slotItemSelection() {
        activityAddAppoitmentBinding!!.spSlot.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if (slotList != null) {
                    selectedSlot = slotList!![p2]
                    //   binding!!.noRecordFound.visibility = View.GONE
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.toolbar_back ->{
                finish()
            }
            R.id.btn_add_booking -> {
                if (TextUtils.isEmpty(date)) run {
                    showDateError(getString(R.string.empty) + " " + "date")
                } else if (TextUtils.isEmpty(subjectId)
                ) run {
                    showSubjectError("Please select subject")
                }else if (TextUtils.isEmpty(selectedSlot) || selectedSlot == "Please Select") run {
                    showSlotError("Please select slot")
                }
                else{
                    startProgressDialog()
                    val mJsonObject = JsonObject()
                    mJsonObject.addProperty("date", date)
                    mJsonObject.addProperty("userId", sharedPrefClass.getPrefValue(this, GlobalConstants.USERID).toString())
                    mJsonObject.addProperty("subjectId", subjectId)
                    mJsonObject.addProperty("time", selectedSlot)
                    mJsonObject.addProperty("credit", "30")
                    bookingAddStudentViewModel.hitAddBookingApi(this,mJsonObject)
                }

            }
            R.id.tv_date ->{
               selectDatePicker()
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
                date = sdf2.format(calendar.time)
                activityAddAppoitmentBinding!!.tvDate.setText(sdf.format(calendar.time))
                val mJsonObject = JsonObject()
                mJsonObject.addProperty("subjectId", subjectId)
                mJsonObject.addProperty("date", date)
                bookingAddStudentViewModel.getSlotList(this, mJsonObject)
            }
        }
        val dpDialog = DatePickerDialog(
            this, date, calendar
                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dpDialog.show()
    }

    override fun onAddBookingSuccess(response: CommonModel) {
        stopProgressDialog()
        finish()
    }

    override fun onAddBookingFailed(message: String) {
        stopProgressDialog()
        showToastError(message)
    }

    override fun onGetSlotListSuccess(response: SlotListResponse) {
        slotList!!.clear()
        slotList!!.add(0,"Please Select")
        if (response.result!=null && !response.result!!.isEmpty()) {
            slotList!!.addAll(response.result!!)
        }
        val adapter = SlotListDropdownAdapter(this, slotList)
        activityAddAppoitmentBinding!!.spSlot.adapter = adapter
    }

    override fun onGetSlotListFailed(message: String) {

    }

    private fun showDateError(emailError: String) {
        activityAddAppoitmentBinding.tvDate.error = emailError
        activityAddAppoitmentBinding.tvDate.requestFocus()
    }

    private fun showSubjectError(passError: String) {
        showToastError(passError)
    }

    private fun showSlotError(passError: String) {
        showToastError(passError)
    }
}