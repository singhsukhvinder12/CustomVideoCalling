package com.customvideocalling.views.authentication

import android.content.Intent
import android.text.TextUtils
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.gson.JsonObject
import com.customvideocalling.R
import com.customvideocalling.application.MyApplication
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityLoginBinding
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.LoginViewModel
import com.customvideocalling.views.MainActivity

class LoginActivity : BaseActivity() {
    private lateinit var activityLoginbinding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private var sharedPrefClass = SharedPrefClass()

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }

    override fun initViews() {
        activityLoginbinding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        activityLoginbinding.loginViewModel = loginViewModel
        // activityLoginbinding.tvForgotPassword.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        loginViewModel.isClick().observe(
                this, Observer<String>(function =
        fun(it: String?) {
            val email = activityLoginbinding.etEmail.text.toString()
            val password = activityLoginbinding.etPassword.text.toString()
            when (it) {
                "btn_login" -> {
                    if (TextUtils.isEmpty(email)) run {
                        showEmailError(getString(R.string.empty) + " " + getString(
                                R.string.email_user_phone))

                    } else if (TextUtils.isEmpty(
                                    password
                            )
                    ) run {
                        showPasswordError(getString(R.string.empty) + " " + getString(
                                R.string.password
                        ))
                    }
                    else {
                        val mJsonObject = JsonObject()
                        mJsonObject.addProperty("email", email)
                        mJsonObject.addProperty("password", password)
                        mJsonObject.addProperty("mobilePhone", "")
                        mJsonObject.addProperty("token", "")
                        loginViewModel.hitLoginApi(mJsonObject)

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







        loginViewModel.getList().observe(this,
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




        loginViewModel.isLoading().observe(this, Observer<Boolean> { aBoolean ->
            if (aBoolean!!) {
                startProgressDialog()
            } else {
                stopProgressDialog()
            }
        })

    }

    private fun showEmailError(emailError: String) {
        activityLoginbinding.etEmail.error = emailError
        activityLoginbinding.etEmail.requestFocus()
    }

    private fun showPasswordError(passError: String) {
        activityLoginbinding.etPassword.requestFocus()
        activityLoginbinding.etPassword.error = passError
    }

}