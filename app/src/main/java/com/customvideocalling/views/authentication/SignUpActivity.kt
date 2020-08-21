package com.customvideocalling.views.authentication

import android.content.Intent
import android.text.TextUtils
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
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.SignUpViewModel
import com.customvideocalling.views.MainActivity
import com.example.artha.model.CommonModel
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject


class SignUpActivity : BaseActivity(), CallBackResult.AddDeviceTokenCallBack {
    private lateinit var activitySignUpBinding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var isTeacher = true
    private var isStudent = false

    override fun initViews() {
        activitySignUpBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)
        signUpViewModel = ViewModelProviders.of(this).get(SignUpViewModel::class.java)
        activitySignUpBinding.signUpViewModel = signUpViewModel
        signUpViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                val userName = activitySignUpBinding.etUserName.text.toString()
                val email = activitySignUpBinding.etEmail.text.toString()
                val password = activitySignUpBinding.etPassword.text.toString()
                val confirmPassword = activitySignUpBinding.etConfirmPassword.text.toString()
                activitySignUpBinding.rgUserType.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
                    if(i == R.id.rb_teacher){
                        isTeacher = true
                        isStudent = false
                    }else if(i == R.id.rb_student){
                        isTeacher = false
                        isStudent = true
                    }
                })
                when (it) {
                    "btn_login" -> {
                        if(TextUtils.isEmpty(userName)) run{
                            showUserNameError(getString(R.string.empty) + " " + getString(
                                R.string.user_name))
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
                                val mJsonObject = JsonObject()
                                mJsonObject.addProperty("email", email)
                                mJsonObject.addProperty("password", password)
                                mJsonObject.addProperty("lName", "aaaa")
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
                                mJsonObject.addProperty("planId", "7")
                                mJsonObject.addProperty("amount", "100")
                                mJsonObject.addProperty("info", "")
                                mJsonObject.addProperty("grade", "")
                                mJsonObject.addProperty("needed", "")
                                mJsonObject.addProperty("help", "")
                                mJsonObject.addProperty("superhero", "")
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
                            }

                        }

                    }
                    "tv_forgot_password" -> {
                        // val intent = Intent(MyApplication.instance, ForgotPasswrodActivity::class.java)
                        //intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                        //  MyApplication.instance.startActivity(intent)

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

    override fun onAddDeviceTokenSuccess(response: CommonModel) {

    }

    override fun onAddDeviceTokenFailed(message: String) {
    }
}