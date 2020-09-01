package com.customvideocalling.views.teacher

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.adapters.SlotListDropdownAdapter
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityAddAppoitmentBinding
import com.customvideocalling.databinding.ActivityFeedBackBinding
import com.customvideocalling.databinding.ActivityScheduleDetailBinding
import com.customvideocalling.utils.DateTimeUtil
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.AddScheduleViewModel
import com.customvideocalling.viewmodels.BookingAddStudentViewModel
import com.customvideocalling.viewmodels.FeedBackViewModel
import com.example.artha.model.CommonModel
import com.google.gson.JsonObject
import java.text.SimpleDateFormat
import java.util.*


class FeedBackActivity : BaseActivity(), CallBackResult.AddBookingCallBack {
    private lateinit var activityFeedBackBinding: ActivityFeedBackBinding
    private lateinit var feedBackViewModel: FeedBackViewModel
    private var growth = ""
    private var name = ""
    private var email = ""
    private var bookingDateTime = ""
    private var sharedPrefClass = SharedPrefClass()
    private var spinnerList: ArrayList<String>?= ArrayList()
    private var progressOfTheSession = 0

    override fun getLayoutId(): Int {
        return R.layout.activity_feed_back
    }

    override fun initViews() {
        activityFeedBackBinding=viewDataBinding as ActivityFeedBackBinding
        feedBackViewModel = ViewModelProviders.of(this).get(FeedBackViewModel::class.java)
        activityFeedBackBinding!!.toolBar.toolbarText.setText(getString(R.string.add_feedback))
        if (intent.hasExtra("email")) {
            val extras = intent.extras
            email = (extras!!.getString("email")).toString()
        }
        if (intent.hasExtra("name")) {
            val extras = intent.extras
            name = (extras!!.getString("name")).toString()
        }
        if (intent.hasExtra("bookingDateTime")) {
            val extras = intent.extras
            bookingDateTime = (extras!!.getString("bookingDateTime")).toString()
        }

        activityFeedBackBinding!!.toolBar.toolbarBack.setOnClickListener{
            finish()
        }
        activityFeedBackBinding.etTutorName.setText(name)
        activityFeedBackBinding.etTutorName.isEnabled = false
        activityFeedBackBinding.etDateTime.setText(bookingDateTime)
        activityFeedBackBinding.etDateTime.isEnabled = false
        spinnerList!!.add("Progress of the session")
        spinnerList!!.add("Good")
        spinnerList!!.add("Average")
        spinnerList!!.add("Very Good")
        spinnerList!!.add("Excellent")
        val adapter = SlotListDropdownAdapter(this, spinnerList)
        activityFeedBackBinding.spProgress.adapter = adapter
        activityFeedBackBinding.spProgress.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                progressOfTheSession = position
            }
        }

        activityFeedBackBinding.etDate.setOnClickListener {
            selectDatePicker()
        }

        activityFeedBackBinding.grothSeekBarID.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                growth = p1.toString()
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        activityFeedBackBinding.btnAddFeedback.setOnClickListener {
            val subject = activityFeedBackBinding.etSubject.text.toString()
            val dateTime = activityFeedBackBinding.etDateTime.text.toString()
            val tutorName = activityFeedBackBinding.etTutorName.text.toString()
            val fourStep = activityFeedBackBinding.etFourStep.text.toString()
            val remember = activityFeedBackBinding.etRemember.text.toString()
            val learned = activityFeedBackBinding.etLearned.text.toString()
            val exercise = activityFeedBackBinding.etExercises.text.toString()
            val tips = activityFeedBackBinding.etTips.text.toString()
            val date = activityFeedBackBinding.etDate.text.toString()
            val mJsonObject = JsonObject()
            mJsonObject.addProperty("amount", date)
            mJsonObject.addProperty("datetime", dateTime)
            mJsonObject.addProperty("exercises", exercise)
            mJsonObject.addProperty("learned", learned)
            mJsonObject.addProperty("parentEmail", email)
            mJsonObject.addProperty("parentName", name)
            mJsonObject.addProperty("progress", progressOfTheSession.toString())
            mJsonObject.addProperty("rating", growth)
            mJsonObject.addProperty("rememberThings", remember)
            mJsonObject.addProperty("step1", fourStep)
            mJsonObject.addProperty("step2", "")
            mJsonObject.addProperty("step3", "")
            mJsonObject.addProperty("step4", "")
            mJsonObject.addProperty("studentEmail",email)
            mJsonObject.addProperty("studentName", name)
            mJsonObject.addProperty("subject", subject)
            mJsonObject.addProperty("tips", tips)
            mJsonObject.addProperty("tutorName", tutorName)
            mJsonObject.addProperty("userId", sharedPrefClass.getPrefValue(this, GlobalConstants.USERID).toString())
            feedBackViewModel.hitAddFeedbackApi(this, mJsonObject)
        }
    }

    override fun onAddBookingSuccess(response: CommonModel) {
        finish()
    }

    override fun onAddBookingFailed(message: String) {
      showToastError(message)
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
               // startDate = sdf2.format(calendar.time)
                activityFeedBackBinding.etDate.setText(sdf.format(calendar.time))
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