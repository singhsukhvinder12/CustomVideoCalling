package com.customvideocalling.views.authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.R
import com.customvideocalling.application.MyApplication
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivitySignUpBinding
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.LoginViewModel
import com.customvideocalling.viewmodels.SignUpViewModel
import com.customvideocalling.views.MainActivity
import com.google.gson.JsonObject

class SignUpActivity : BaseActivity() {
    private lateinit var activitySignUpBinding: ActivitySignUpBinding
    private lateinit var signUpViewModel: SignUpViewModel
    private var sharedPrefClass = SharedPrefClass()

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
                            val mJsonObject = JsonObject()
                            mJsonObject.addProperty("email", email)
                            mJsonObject.addProperty("password", password)
                            mJsonObject.addProperty("lName", "")
                            mJsonObject.addProperty("fName", userName)
                            mJsonObject.addProperty("rollNo", "12")
                            mJsonObject.addProperty("dob", "1995-12-24")
                            mJsonObject.addProperty("class", "11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000")
                            mJsonObject.addProperty("house", "11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000")
                            mJsonObject.addProperty("section", "11bf5b37-e0b8-42e0-8dcf-dc8c4aefc000")
                            mJsonObject.addProperty("planId", "7")
                            mJsonObject.addProperty("amount", "100")
                            mJsonObject.addProperty("info", "")
                            mJsonObject.addProperty("grade", "")
                            mJsonObject.addProperty("needed", "")
                            mJsonObject.addProperty("help", "")
                            mJsonObject.addProperty("superhero", "")
                            signUpViewModel.hitStudentSignUpApi(mJsonObject)

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
//                            SharedPrefClass().putObject(
//                                    MyApplication.instance,
//                                    GlobalConstants.CUSTOMER_IAMGE,
//                                    loginResponse.result!!.userIm
//                            )


                        showToastSuccess(message)
                        val intent = Intent(this, MainActivity::class.java)
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
}