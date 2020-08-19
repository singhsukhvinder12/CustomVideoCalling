package com.customvideocalling.utils.core

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.os.StrictMode
import android.view.LayoutInflater
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.gson.GsonBuilder
import com.customvideocalling.R
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.utils.PermissionUtil
import com.customvideocalling.views.authentication.LoginActivity

/**
 * Created by Saira on 2018-12-9.
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var viewDataBinding: ViewDataBinding? = null
    private var inputMethodManager: InputMethodManager? = null
    private var progressDialog: Dialog? = null
    private var mFragmentManager: FragmentManager? = null
    private val gson = GsonBuilder().serializeNulls().create()
    private var permCallback: PermissionCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        this@BaseActivity.overridePendingTransition(
                R.anim.slide_in,
                R.anim.slide_out
        )

        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId())
        //  mtoolbar = findViewById(R.id.toolbar);
        //        retrofitClient = (ApiService) RetrofitClient.with(this).getClient(Constants.API_BASE_URL, false, this).create(ApiService.class);
        inputMethodManager = this
                .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //store = new PrefStore(this);
        //    public ApiService retrofitClient;
        strictModeThread()
        initializeProgressDialog()
        initViews()

    }

    protected abstract fun initViews()
    protected abstract fun getLayoutId(): Int

    private fun strictModeThread() {
        StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                        .permitAll().build()
        )
    }

    fun eventCreatedDialog(activity: Activity, key: String, message: String) {
        showToastSuccess(message)
        val dialog = Dialog(activity)
        dialog.setContentView(R.layout.activity_dialog_tick)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val imgTick = dialog.findViewById(R.id.img_tick) as ImageView
        object : CountDownTimer(500, 200) {
            override fun onTick(millisUntilFinished: Long) {
                (imgTick.drawable as Animatable).start()
            }

            override fun onFinish() {
                when (key) {
                    "reset_password" -> {
                        val intent1 = Intent(activity, LoginActivity::class.java)
                        activity.startActivity(intent1)
                        ActivityCompat.finishAffinity(activity)
                    }
                    "cancel_activity" -> {
                        activity.finish()
                    }
                    "change_password" -> {
                        activity.finish()
                    }
                    "cancel" -> dialog.dismiss()
                    "update" -> dialog.dismiss()
                    "update_profile" -> dialog.dismiss()
                }
            }

        }.start()
        dialog.show()

    }

    fun checkObjectNull(obj: Any?): Boolean {
        return obj != null
    }

    fun showAlert(activity: Activity, message1: String) {
        val binding =
                DataBindingUtil.inflate<ViewDataBinding>(
                        LayoutInflater.from(activity),
                        R.layout.layout_custom_alert,
                        null,
                        false
                )
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.setTitle(getString(R.string.app_name))
        // set the custom dialog components - text, image and button
        val text = dialog.findViewById(R.id.text) as TextView
        text.text = message1
        val dialogButton = dialog.findViewById(R.id.dialogButtonOK) as Button
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    /*}
     *Method called  progress dialog */
    private fun initializeProgressDialog() {
        progressDialog = Dialog(this, R.style.transparent_dialog_borderless)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(this),
                R.layout.progress_dialog,
                null,
                false
        )
        progressDialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressDialog!!.setContentView(binding.root)

        mFragmentManager = supportFragmentManager
        // txtMsgTV = (TextView) view.findViewById(R.id.txtMsgTV);
        progressDialog!!.setCancelable(false)
    }

    fun showToastSuccess(message: String?) {
        UtilsFunctions.showToastSuccess(message!!)

    }

    fun showToastError(message: String?) {
        UtilsFunctions.showToastError(message!!)

    }

    private fun showToastWarning(message: String?) {
        UtilsFunctions.showToastWarning(message)

    }

    /*
     * Method to show snack bar*/
    /*
     * Method to start progress dialog*/
    fun startProgressDialog() {
        if (progressDialog != null && !progressDialog!!.isShowing) {
            try {
                progressDialog!!.show()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    /*
     * Method to stop progress dialog*/
    fun stopProgressDialog() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            try {
                progressDialog!!.dismiss()
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }
    }

    fun callFragments(
            fragment: Fragment?,
            mFragmentManager: FragmentManager,
            mSendDataCheck: Boolean,
            key: String?,
            mObject: Any
    ) {
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        if (fragment != null) {
            if (mSendDataCheck && key != null) {
                when (key) {
                    "deleteAll" -> mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    "send_data" -> {
                        val mBundle = Bundle()
                        mBundle.putString(GlobalConstants.SEND_DATA, gson.toJson(mObject))
                        fragment.arguments = mBundle
                    }
                }
            }
            mFragmentTransaction.addToBackStack(null)
            // mFragmentTransaction.replace(R.id.frame_layout, fragment)
            // mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            mFragmentTransaction.commit()
        }
    }

    fun callFragmentsContainer(
        fragment: Fragment?,
        mFragmentManager: FragmentManager,
        mSendDataCheck: Boolean,
        key: String?,
        mObject: Any,
        id: Int
    ) {
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        if (fragment != null) {
            if (mSendDataCheck && key != null) {
                when (key) {
                    "deleteAll" -> mFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    "send_data" -> {
                        val mBundle = Bundle()
                        mBundle.putString(GlobalConstants.SEND_DATA, gson.toJson(mObject))
                        fragment.arguments = mBundle
                    }
                }
            }
            mFragmentTransaction.addToBackStack(null)
            mFragmentTransaction.replace(id, fragment)
            // mFragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            mFragmentTransaction.commit()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        PermissionUtil.onRequestPermissionsResult(requestCode, permissions, grantResults, this, permCallback)
    }

    interface PermissionCallback {
        fun permGranted()

        fun permDenied()
    }

}
