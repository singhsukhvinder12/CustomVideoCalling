package com.customvideocalling.views

import android.content.Context
import android.content.Intent
import com.customvideocalling.R
import com.customvideocalling.application.MyApplication
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivitySplashBinding
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.views.authentication.LoginActivity
import com.customvideocalling.views.student.AddTokentActivity
import com.customvideocalling.views.student.TokenHistoryActivity
import com.customvideocalling.views.teacher.AddScheduleActivity
import com.google.firebase.iid.FirebaseInstanceId
import java.util.*

class SplashActivity : BaseActivity() {
    private var mActivitySplashBinding: ActivitySplashBinding? = null
    private var sharedPrefClass=SharedPrefClass()
    private var mContext: Context? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_splash
    }

    override fun initViews() {
        mContext = this
        mActivitySplashBinding = viewDataBinding as ActivitySplashBinding
        val token: String? = "sd"

        if (token != null) {
            sharedPrefClass.putObject(
                    applicationContext,
                    GlobalConstants.NOTIFICATION_TOKEN,
                    token
            )
        }

        Timer().schedule(object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    checkScreenType()
                }
            }
        }, 3000)
    }

    private fun checkScreenType() {
        var sharedPref = SharedPrefClass()
        sharedPref.putObject(MyApplication.instance.applicationContext,GlobalConstants.DEVICETOKEN,FirebaseInstanceId.getInstance().getToken())
        var login = ""
        if (checkObjectNull(
                        SharedPrefClass().getPrefValue(
                                this,
                                "isLogin"
                        )
                )
        )
            login = sharedPrefClass.getPrefValue(this, "isLogin").toString()
        val intent = if (login == "true") {
            if (sharedPrefClass.getPrefValue(this,GlobalConstants.TYPE)=="1") {
                Intent(this, MainActivity::class.java)
            }else{
                Intent(this, TeacherMainActivity::class.java)
            }
        } else {
            Intent(this, LoginActivity::class.java)

        }

        startActivity(intent)
        finish()
    }

}
