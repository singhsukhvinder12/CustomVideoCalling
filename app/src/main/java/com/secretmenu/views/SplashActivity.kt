package com.secretmenu.views

import android.content.Context
import android.content.Intent
import com.secretmenu.R
import com.secretmenu.constants.GlobalConstants
import com.secretmenu.databinding.ActivitySplashBinding
import com.secretmenu.utils.SharedPrefClass
import com.secretmenu.utils.core.BaseActivity
import com.secretmenu.views.authentication.LoginActivity
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
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, LoginActivity::class.java)

        }

        startActivity(intent)
        finish()
    }

}
