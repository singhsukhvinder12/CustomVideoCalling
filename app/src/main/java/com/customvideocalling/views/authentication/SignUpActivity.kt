package com.customvideocalling.views.authentication

import android.R.attr
import android.app.Activity
import android.content.Intent
import android.text.TextUtils
import android.view.View
import android.widget.RadioGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.application.MyApplication
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivitySignUpBinding
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.utils.AlertDialogUtil
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.SignUpViewModel
import com.customvideocalling.views.MainActivity
import com.customvideocalling.views.student.AddTokentActivity
import com.example.artha.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject


class SignUpActivity : BaseActivity(), CallBackResult.AddDeviceTokenCallBack, CallBackResult.AddQuestionnaireCallBack {
    private lateinit var activitySignUpBinding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var isTeacher = false
    private var isStudent = false
    private var mInfo = ""
    private var mGrade = ""
    private var mNeed = ""
    private var mHelp = ""
    private var mSuperhero = ""
    private var planId = ""
    private var amount = ""

    override fun initViews() {
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        activitySignUpBinding.signUpViewModel = signUpViewModel
        activitySignUpBinding.rgUserType.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            if(i == R.id.rb_teacher){
                isTeacher = true
                isStudent = false
                activitySignUpBinding.btnSelectPlan.visibility = View.GONE
                activitySignUpBinding.btnSelectQuestionnaire.visibility = View.GONE
            }else if(i == R.id.rb_student){
                activitySignUpBinding.btnSelectPlan.visibility = View.VISIBLE
                activitySignUpBinding.btnSelectQuestionnaire.visibility = View.VISIBLE
                isTeacher = false
                isStudent = true
            }
        })
        signUpViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                val userName = activitySignUpBinding.etUserName.text.toString()
                val lastName = activitySignUpBinding.etLastName.text.toString()
                val email = activitySignUpBinding.etEmail.text.toString()
                val password = activitySignUpBinding.etPassword.text.toString()
                val confirmPassword = activitySignUpBinding.etConfirmPassword.text.toString()
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
                            if (isStudent) {
                                if(planId.isEmpty() && amount.isEmpty()){
                                    showPlanAmountError("Please Select plan")
                                    return
                                }
                                val mJsonObject = JsonObject()
                                mJsonObject.addProperty("email", email)
                                mJsonObject.addProperty("password", password)
                                mJsonObject.addProperty("lName", lastName)
                                mJsonObject.addProperty("fName", userName)
                                mJsonObject.addProperty("rollNo", "12")
                                mJsonObject.addProperty("dob", "1995-12-24")
                                mJsonObject.addProperty(
                                    "class",
                                    "11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000"
                                )
                                mJsonObject.addProperty(
                                    "house",
                                    "11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000"
                                )
                                mJsonObject.addProperty(
                                    "section",
                                    "11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000"
                                )
                                mJsonObject.addProperty("planId", planId)
                                mJsonObject.addProperty("amount", amount)
                                mJsonObject.addProperty("info", mInfo)
                                mJsonObject.addProperty("grade", mGrade)
                                mJsonObject.addProperty("needed", mNeed)
                                mJsonObject.addProperty("help", mHelp)
                                mJsonObject.addProperty("superhero", mSuperhero)
                                signUpViewModel.hitStudentSignUpApi(mJsonObject)
                            }else if(isTeacher){
                                val gson = GsonBuilder().setPrettyPrinting().create()
                                val array = JsonArray()
                                array.add("11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000")
                                array.add("75442486-0878-440c-9db1-a7006c25a39f")
                                val mJsonObject = JsonObject()
                                mJsonObject.addProperty("email", email)
                                mJsonObject.addProperty("password", password)
                                mJsonObject.addProperty("lName", "aaaa")
                                mJsonObject.addProperty("fName", userName)
                                mJsonObject.addProperty("teacherId", "1234567")
                                mJsonObject.addProperty("dob", "1995-12-24")
                                mJsonObject.addProperty(
                                    "faculty",
                                    "11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000"
                                )
                                mJsonObject.addProperty(
                                    "designation",
                                    "11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000"
                                )
                                mJsonObject.addProperty("subjects",gson.toJson(array))
                                signUpViewModel.hitTeacherSignUpApi(mJsonObject)
                            }else{
                                showToastError("Please select user type")
                            }

                        }

                    }
                    "btn_select_plan" -> {
                         val intent = Intent(this, AddTokentActivity::class.java)
                          intent.putExtra("throughSignUp","1")
                        startActivityForResult(intent,1)

                    }

                    "btn_select_questionnaire" ->{
                        AlertDialogUtil.showAddChapterDialog(this,this)
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
                        mJsonObject.addProperty("deviceToken", sharedPrefClass.getPrefValue(this,GlobalConstants.DEVICETOKEN).toString())
                        mJsonObject.addProperty("userId", sharedPrefClass.getPrefValue(this,GlobalConstants.USERID).toString())
                        /*mJsonObject.addProperty("mobilePhone", "")
                        mJsonObject.addProperty("token", "")*/
                        signUpViewModel.addDeviceToken(this, mJsonObject)
                        showToastSuccess(message)
                        val intent = Intent(this, MainActivity::class.java)
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

    override fun getLayoutId(): Int {
        return R.layout.activity_sign_up
    }

    private fun showEmailError(emailError: String) {
        activitySignUpBinding.etEmail.error = emailError
        activitySignUpBinding.etEmail.requestFocus()
    }

    private fun showPasswordError(passError: String) {
        activitySignUpBinding.etPassword.requestFocus()
        activitySignUpBinding.etPassword.error = passError
    }

    private fun showConfirmPasswordError(passError: String) {
        activitySignUpBinding.etConfirmPassword.requestFocus()
        activitySignUpBinding.etConfirmPassword.error = passError
    }

    private fun showUserNameError(passError: String) {
        activitySignUpBinding.etUserName.requestFocus()
        activitySignUpBinding.etUserName.error = passError
    }

    private fun showLastNameError(passError: String) {
        activitySignUpBinding.etLastName.requestFocus()
        activitySignUpBinding.etLastName.error = passError
    }

    private fun showPlanAmountError(passError: String) {
       showToastError(passError)
    }

    override fun onAddDeviceTokenSuccess(response: CommonModel) {

    }

    override fun onAddDeviceTokenFailed(message: String) {
    }

    override fun onAddQuestionnaire(
        info: String,
        grade: String,
        need: String,
        help: String,
        superhero: String
    ) {
        mInfo = info
        mGrade = grade
        mNeed = need
        mHelp = help
        mSuperhero = superhero
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode === 1) {
            if (resultCode === Activity.RESULT_OK) {
               planId = data!!.getStringExtra("planId")!!
               amount = data.getStringExtra("amount")!!
            }
        }
    }
}