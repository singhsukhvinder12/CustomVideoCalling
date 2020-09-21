package com.customvideocalling.views.authentication

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.adapters.SlotListDropdownAdapter
import com.customvideocalling.adapters.SubjectListDropdownAdapter
import com.customvideocalling.application.MyApplication
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityTeacherSignupBinding
import com.customvideocalling.model.ClassSubjectListResponse
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.model.SubjectListMultipleModel
import com.customvideocalling.utils.ConvertBase64
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.SignUpViewModel
import com.customvideocalling.views.MainActivity
import com.customvideocalling.views.TeacherMainActivity
import com.esafirm.imagepicker.features.ImagePicker
import com.esafirm.imagepicker.features.ReturnMode
import com.example.artha.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.jaiselrahman.filepicker.activity.FilePickerActivity
import com.jaiselrahman.filepicker.config.Configurations
import com.jaiselrahman.filepicker.model.MediaFile
import com.uniongoods.adapters.SubjectListMultipleAdapter
import com.uniongoods.adapters.TimeListAdapter
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class TeacherSignupActivity : BaseActivity(), CallBackResult.AddDeviceTokenCallBack,
    CallBackResult.ClassSubjectListCallBack{
    private lateinit var binding: ActivityTeacherSignupBinding
    private lateinit var signUpViewModel: SignUpViewModel
    private var sharedPrefClass = SharedPrefClass()
    val RC_CODE_PICKER = 2000
    val FILE_REQUEST_CODE_document = 3000
    val FILE_REQUEST_CODE_report = 4000
    val FILE_REQUEST_CODE_cv = 5000
    private var grade = ""
    private var hours = ""
    var images: MutableList<com.esafirm.imagepicker.model.Image> = mutableListOf()
    var filesDocument: ArrayList<MediaFile>? = ArrayList()
    var filesReport: ArrayList<MediaFile>? = ArrayList()
    var filesCV: ArrayList<MediaFile>? = ArrayList()
     var selectedImage = ""
     var selectedDocument = ""
     var selectedReport = ""
     var selectedCV = ""
    private var subjectId = ""
    private var isArrested = false
    private var isCharged = false
    private var isOverEighteen = "No"
    private var arrested = "0"
    private var charged = "0"
    private var spinnerList: ArrayList<String>?= ArrayList()
    private var availableHoursList: ArrayList<String>?= ArrayList()
    var licenseImageFile: File? = null
    var documentFile: File? = null
    var reportFile: File? = null
    var cvFile: File? = null
    private var subjectList: ArrayList<ClassSubjectListResponse.Subject>?=null
    private var subjectListLocal: ArrayList<SubjectListMultipleModel>?=null
    private var subjectListFinal: ArrayList<SubjectListMultipleModel>?=null
    lateinit var pw2: PopupWindow




    override fun getLayoutId(): Int {
        return R.layout.activity_teacher_signup
    }

    override fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_signup)
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        binding.signUpViewModel = signUpViewModel
        binding.llSignupCrime.visibility = View.GONE
        binding.llSignupArrest.visibility = View.GONE
        subjectList = ArrayList()
        subjectListLocal = ArrayList()
        subjectListFinal = ArrayList()
        signUpViewModel.getSubjectList(this)
        //subjectItemSelection()
        binding.spSubject.setOnClickListener {
            pw2!!.showAsDropDown(binding!!.spSubject)
        }
        spinnerList!!.add("Please Select Grade")
        spinnerList!!.add("First Grade")
        spinnerList!!.add("Second Grade")
        spinnerList!!.add("Third Grade")
        spinnerList!!.add("Fourth Grade")
        spinnerList!!.add("Fifth Grade")
        spinnerList!!.add("Sixth Grade")
        spinnerList!!.add("Seventh Grade")
        spinnerList!!.add("Eighth Grade")
        spinnerList!!.add("Ninth Grade")
        spinnerList!!.add("Tenth Grade")
        spinnerList!!.add("Eleventh Grade")
        spinnerList!!.add("Twelfth Grade")
        availableHoursList!!.add("Please Select Available Hours")
        availableHoursList!!.add("08:00 AM - 05:00 PM")
        availableHoursList!!.add("09:00 AM - 06:00 PM")
        availableHoursList!!.add("10:00 AM - 07:00 PM")
        availableHoursList!!.add("11:00 AM - 08:00 PM")
        availableHoursList!!.add("12:00 PM - 09:00 PM")
        val hourAdapter = SlotListDropdownAdapter(this, availableHoursList)
        val adapter = SlotListDropdownAdapter(this, spinnerList)
        binding.spGrade.adapter = adapter
        binding.spGrade?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                grade = spinnerList!![position]
            }
        }
        binding.etHours.adapter = hourAdapter
        binding.etHours?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                hours = availableHoursList!![position]
            }
        }
        binding.checkBoxArrest.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                isArrested = true
                arrested = "1"
                binding.llSignupArrest.visibility = View.VISIBLE
            }else{
                isArrested = false
                arrested = "0"
                binding.llSignupArrest.visibility = View.GONE
            }
        }
        binding.checkBoxCrime.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                isCharged = true
                charged = "1"
                binding.llSignupCrime.visibility = View.VISIBLE
            }else{
                isCharged = false
                charged = "0"
                binding.llSignupCrime.visibility = View.GONE
            }
        }
        binding.checkBoxOverEighteen.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked){
                isOverEighteen = "Yes"
            }else{
                isOverEighteen = "No"
            }
        }
        signUpViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                val userName = binding.etUserName.text.toString()
                val lastName = binding.etLastName.text.toString()
                val email = binding.etEmail.text.toString()
                val password = binding.etPassword.text.toString()
                val confirmPassword = binding.etConfirmPassword.text.toString()
                val address = binding.etAddress.text.toString()
                val education = binding.etEducation.text.toString()
                val phone = binding.etPhone.text.toString()
                val hours = hours
                val arrestedDetail = binding.etArrest.text.toString()
                val crime = binding.etCrime.text.toString()
                val bankName = binding.etBankeName.text.toString()
                val userNameBank = binding.etUserNameBank.text.toString()
                val ifscCode = binding.etIFSCCode.text.toString()
                val accountNumber = binding.etAccountNumber.text.toString()
                when (it) {
                    "btn_login" -> {
                        if(TextUtils.isEmpty(userName)) run{
                            showUserNameError(getString(R.string.empty) + " " + "First Name")
                        }
                        else if(TextUtils.isEmpty(lastName)) run{
                            showLastNameError(getString(R.string.empty) + " " + "Last Name")
                        }
                        else if (TextUtils.isEmpty(email)) run {
                            showEmailError(getString(R.string.empty) + " " + getString(
                                R.string.email_user_phone))

                        } else if (TextUtils.isEmpty(password)) run {
                            showPasswordError(getString(R.string.empty) + " " + getString(
                                R.string.password
                            ))
                        }else if (!TextUtils.equals(password, confirmPassword))run {
                            showConfirmPasswordError("Password and confirm password is different")
                        }
                        else {
                             /*   if (grade.isEmpty()){
                                    showPlanAmountError("Please select grade")
                                }else {*/
                            val gson = GsonBuilder().setPrettyPrinting().create()
                            val array = JsonArray()
                            for (i in 0 until subjectListFinal!!.size) {
                                array.add(subjectListFinal!![i].id)
                            }
                                    val mJsonObject = JsonObject()
                                    mJsonObject.addProperty("email", email)
                                    mJsonObject.addProperty("password", password)
                                    mJsonObject.addProperty("lName", lastName)
                                    mJsonObject.addProperty("fName", userName)
                                    mJsonObject.addProperty("education", education)
                                     mJsonObject.addProperty("teacherId", "123456")
                                    mJsonObject.addProperty("address", address)
                                    mJsonObject.addProperty("phoneNo", phone)
                                    mJsonObject.addProperty("grade", grade)
                                    mJsonObject.addProperty("hours", hours)
                                    mJsonObject.addProperty("arrested", arrested)
                                    mJsonObject.addProperty("over18", isOverEighteen)
                                    mJsonObject.addProperty("arrestedDetails",arrestedDetail)
                                    mJsonObject.addProperty("charged", charged)
                                    mJsonObject.addProperty("chargedDetails", crime)
                                    mJsonObject.addProperty("bankName", bankName)
                                    mJsonObject.addProperty("userName", userNameBank)
                                    mJsonObject.addProperty("ifcCode", ifscCode)
                                    mJsonObject.addProperty("accountNumber", accountNumber)
                                    mJsonObject.addProperty("subjects", gson.toJson(array))
                                   /* mJsonObject.addProperty("license", selectedImage)
                                    mJsonObject.addProperty("documents", selectedDocument)
                                    mJsonObject.addProperty("report", selectedReport)
                                    mJsonObject.addProperty("cv", selectedCV)*/
                                    signUpViewModel.hitTeacherSignUpApi(mJsonObject, this)
                               // }

                        }

                    }

                    "tv_driving_license" ->{
                       pickImage()
                    }
                    "tv_higer_degree" ->{
                       pickFile(FILE_REQUEST_CODE_document)
                    }
                    "tv_report_background" ->{
                        pickFile(FILE_REQUEST_CODE_report)
                    }
                    "tv_cv" ->{
                        pickFile(FILE_REQUEST_CODE_cv)
                    }
                }

            })
        )

        signUpViewModel.getList().observe(this,
            Observer<LoginResponse> { loginResponse ->
                stopProgressDialog()


                if (loginResponse != null) {
                    val message = loginResponse.goodsDeliveryMessage

                    if (loginResponse.code == 200) {
                        SharedPrefClass().putObject(
                            MyApplication.instance,
                            "isLogin",
                            true
                        )

                        sharedPrefClass.putObject(
                            MyApplication.instance,
                            GlobalConstants.ACCESS_TOKEN,
                            loginResponse.result!!.token
                        )
                        sharedPrefClass.putObject(
                            MyApplication.instance,
                            GlobalConstants.USERID,
                            loginResponse.result!!.userId
                        )
                        sharedPrefClass.putObject(
                            MyApplication.instance,
                            GlobalConstants.USERNAME,
                            loginResponse.result!!.userName
                        )
                        sharedPrefClass.putObject(
                            MyApplication.instance,
                            GlobalConstants.TYPE,
                            loginResponse.result!!.type
                        )
//                            SharedPrefClass().putObject(
//                                    MyApplication.instance,
//                                    GlobalConstants.CUSTOMER_IAMGE,
//                                    loginResponse.result!!.userIm
//                            )

                        val mJsonObject = JsonObject()
                        mJsonObject.addProperty("deviceToken", sharedPrefClass.getPrefValue(this,
                            GlobalConstants.DEVICETOKEN).toString())
                        mJsonObject.addProperty("userId", sharedPrefClass.getPrefValue(this,
                            GlobalConstants.USERID).toString())
                        /*mJsonObject.addProperty("mobilePhone", "")
                        mJsonObject.addProperty("token", "")*/
                        signUpViewModel.addDeviceToken(this, mJsonObject)
                        showToastSuccess(message)
                            val intent = Intent(this, TeacherMainActivity::class.java)
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        finish()

                    } else {
                        showToastError(message)
                    }

                }
            })




        signUpViewModel.isLoading().observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })
    }

    private fun showEmailError(emailError: String) {
        binding.etEmail.error = emailError
        binding.etEmail.requestFocus()
    }

    private fun showPasswordError(passError: String) {
        binding.etPassword.requestFocus()
        binding.etPassword.error = passError
    }

    private fun showConfirmPasswordError(passError: String) {
        binding.etConfirmPassword.requestFocus()
        binding.etConfirmPassword.error = passError
    }

    private fun showUserNameError(passError: String) {
        binding.etUserName.requestFocus()
        binding.etUserName.error = passError
    }

    private fun showLastNameError(passError: String) {
        binding.etLastName.requestFocus()
        binding.etLastName.error = passError
    }

    private fun showEducationError(passError: String) {
        binding.etEducation.requestFocus()
        binding.etEducation.error = passError
    }

    private fun showAddressError(passError: String) {
        binding.etAddress.requestFocus()
        binding.etAddress.error = passError
    }

   /* private fun showHoursError(passError: String) {
        binding.etHours.requestFocus()
        binding.etHours.error = passError
    }*/

    private fun showBankNameError(passError: String) {
        binding.etBankeName.requestFocus()
        binding.etBankeName.error = passError
    }

    private fun showUserNameBankError(passError: String) {
        binding.etUserNameBank.requestFocus()
        binding.etUserNameBank.error = passError
    }

    private fun showIfcCodeError(passError: String) {
        binding.etIFSCCode.requestFocus()
        binding.etIFSCCode.error = passError
    }

    private fun showAccountNumberError(passError: String) {
        binding.etAccountNumber.requestFocus()
        binding.etAccountNumber.error = passError
    }

    private fun showDrivingLicenseError(passError: String) {
        binding.tvDrivingLicense.requestFocus()
        binding.tvDrivingLicense.error = passError
    }

    private fun showPlanAmountError(passError: String) {
        showToastError(passError)
    }

    private fun pickImage() {
        ImagePicker.create(this)
            .returnMode(ReturnMode.ALL) // set whether pick action or camera action should return immediate result or not. Only works in single mode for image picker
            .folderMode(true) // set folder mode (false by default)
            .single()
            .limit(1)
            .toolbarFolderTitle(getString(R.string.folder)) // folder selection title
            .toolbarImageTitle(getString(R.string.gallery_select_title_msg))
            .start(RC_CODE_PICKER)
    }

    private fun pickFile(requestCode: Int) {
            val intent = Intent(this, FilePickerActivity::class.java)
            intent.putExtra(
                FilePickerActivity.CONFIGS, Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(false)
                .setShowVideos(false)
                .setShowFiles(true)
                .enableImageCapture(false)
                .setSuffixes("doc", "pdf")
                .setMaxSelection(1)
                .setSkipZeroSizeFiles(true)
                .build())
            startActivityForResult(intent, requestCode)
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_CODE_PICKER && resultCode == RESULT_OK && data != null) {
            images = ImagePicker.getImages(data)

            licenseImageFile = File(images.get(0).path)
            selectedImage = images.get(0).name
            binding.tvDrivingLicense.text = selectedImage
            val bm =
                BitmapFactory.decodeFile(/*"/path/to/image.jpg"*/images.get(0).path)
            val baos = ByteArrayOutputStream()
            bm.compress(
                Bitmap.CompressFormat.JPEG,
                100,
                baos
            ) // bm is the bitmap object
        }
        else if (requestCode == FILE_REQUEST_CODE_document && resultCode == RESULT_OK && data != null) {
            filesDocument = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
            documentFile = File(filesDocument!!.get(0).path)
            selectedDocument = filesDocument!!.get(0).name
            binding.tvHigerDegree.text = selectedDocument
        //   selectedDocument = ConvertBase64.getStringFile(File(filesDocument!!.get(0).path))
        }
        else if (requestCode == FILE_REQUEST_CODE_report && resultCode == RESULT_OK && data != null) {
            filesReport = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
            reportFile = File(filesReport!!.get(0).path)
            selectedReport = filesReport!!.get(0).name
            binding.tvReportBackground.text = selectedReport
            //selectedReport = ConvertBase64.getStringFile(File(filesReport!!.get(0).path))

        }
        else if (requestCode == FILE_REQUEST_CODE_cv && resultCode == RESULT_OK && data != null) {
            filesCV = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES)
            cvFile = File(filesCV!!.get(0).path)
            selectedCV = filesCV!!.get(0).name
            binding.tvCv.text = selectedCV
           // selectedCV = ConvertBase64.getStringFile(File(filesCV!!.get(0).path))

        }
    }

    override fun onAddDeviceTokenSuccess(response: CommonModel) {

    }

    override fun onAddDeviceTokenFailed(message: String) {

    }

    /*private fun subjectItemSelection() {
        binding!!.spSubject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                if (subjectList != null) {
                    subjectId = subjectList!![p2].id!!
                    //   binding!!.noRecordFound.visibility = View.GONE
                }
            }
        }
    }*/

    override fun onGetClassSubjectListSuccess(response: ClassSubjectListResponse) {
        stopProgressDialog()
        for ( i in 0 until response.result!!.subjectList!!.size){
           subjectListLocal!!.add(SubjectListMultipleModel(response.result!!.subjectList!![i].name!!,
               "false", response.result!!.subjectList!![i].id!!))
        }
        var inflater2 = this.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var views2=inflater2!!.inflate(R.layout.days_dialog, null, false)
        pw2 =  PopupWindow(views2, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true)
        pw2!!.setOutsideTouchable(true);
        var daysRecyclerView2=views2.findViewById<RecyclerView>(R.id.rvDays)


        val adapter2 = SubjectListMultipleAdapter(this, subjectListLocal!!)
        var layout2= LinearLayoutManager(this)
        daysRecyclerView2.adapter=adapter2
        daysRecyclerView2.layoutManager=layout2

    }

    override fun onGetClassSubjectListFailed(message: String) {

    }

    fun updateValueForSubject(){

        var multipleValue=""
        subjectListFinal = ArrayList()
        for ( i in 0 until subjectListLocal!!.size){

            if (subjectListLocal!!.get(i).isSelected.equals("true")){
                subjectListFinal!!.add(subjectListLocal!!.get(i))
                if (multipleValue.isEmpty()){
                    multipleValue=subjectListLocal!!.get(i).name
                }else{
                    multipleValue=multipleValue+","+subjectListLocal!!.get(i).name

                }

            }

        }

        if (multipleValue.isEmpty()){
            binding!!.spSubject.text="Please Select Subject"
        }else{
            binding!!.spSubject.text=multipleValue

        }

    }
}