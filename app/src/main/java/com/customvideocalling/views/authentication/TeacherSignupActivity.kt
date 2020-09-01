package com.customvideocalling.views.authentication

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.net.toFile
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.adapters.SlotListDropdownAdapter
import com.customvideocalling.application.MyApplication
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityTeacherSignupBinding
import com.customvideocalling.model.LoginResponse
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
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList

class TeacherSignupActivity : BaseActivity(), CallBackResult.AddDeviceTokenCallBack {
    private lateinit var binding: ActivityTeacherSignupBinding
    private lateinit var signUpViewModel: SignUpViewModel
    private var sharedPrefClass = SharedPrefClass()
    val RC_CODE_PICKER = 2000
    val FILE_REQUEST_CODE_document = 3000
    val FILE_REQUEST_CODE_report = 4000
    val FILE_REQUEST_CODE_cv = 5000
    private var grade = ""
    var images: MutableList<com.esafirm.imagepicker.model.Image> = mutableListOf()
    var filesDocument: ArrayList<MediaFile>? = ArrayList()
    var filesReport: ArrayList<MediaFile>? = ArrayList()
    var filesCV: ArrayList<MediaFile>? = ArrayList()
     var selectedImage = ""
     var selectedDocument = ""
     var selectedReport = ""
     var selectedCV = ""
    private var isArrested = false
    private var isCharged = false
    private var arrested = "0"
    private var charged = "0"
    private var spinnerList: ArrayList<String>?= ArrayList()
    var licenseImageFile: File? = null
    var documentFile: File? = null
    var reportFile: File? = null
    var cvFile: File? = null




    override fun getLayoutId(): Int {
        return R.layout.activity_teacher_signup
    }

    override fun initViews() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_teacher_signup)
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        binding.signUpViewModel = signUpViewModel
        binding.llSignupCrime.visibility = View.GONE
        binding.llSignupArrest.visibility = View.GONE
        spinnerList!!.add("Please Select")
        spinnerList!!.add("Level 1")
        spinnerList!!.add("Level 2")
        val adapter = SlotListDropdownAdapter(this, spinnerList)
        binding.spGrade.adapter = adapter
        binding.spGrade?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                grade = spinnerList!![position]
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
                val hours = binding.etHours.text.toString()
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
                            array.add("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000")
                            array.add("75442486-0878-440c-9db1-a7006c25a39f")
                                    val mJsonObject = JsonObject()
                                    mJsonObject.addProperty("email", email)
                                    mJsonObject.addProperty("password", password)
                                    mJsonObject.addProperty("lName", lastName)
                                    mJsonObject.addProperty("fName", userName)
                                    mJsonObject.addProperty("education", education)
                                     mJsonObject.addProperty("teacherId", "123456")
                                    mJsonObject.addProperty("address", address)
                                    mJsonObject.addProperty("grade", grade)
                                    mJsonObject.addProperty("hours", hours)
                                    mJsonObject.addProperty("arrested", arrested)
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

    private fun showHoursError(passError: String) {
        binding.etHours.requestFocus()
        binding.etHours.error = passError
    }

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
}