package com.customvideocalling.views.student

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.Interfaces.CallBackResult
import com.customvideocalling.R
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityAddTokentBinding
import com.customvideocalling.databinding.ActivityPaymentBinding
import com.customvideocalling.utils.SharedPrefClass
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.AddTokenViewModel
import com.example.artha.model.CommonModel
import com.google.gson.JsonObject

class PaymentActivity : BaseActivity(), View.OnClickListener, CallBackResult.PaymentCallBack {
    private lateinit var activityPaymentBinding: ActivityPaymentBinding
    private lateinit var addTokenViewModel: AddTokenViewModel
    private var sharedPrefClass = SharedPrefClass()
    private var planId = ""
    private var amount = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_payment
    }

    override fun initViews() {
        activityPaymentBinding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        addTokenViewModel = ViewModelProviders.of(this).get(AddTokenViewModel::class.java)
        activityPaymentBinding.toolBar.toolbarText.setText(getString(R.string.add_token))
        activityPaymentBinding.toolBar.toolbarBack.setOnClickListener(this)
        activityPaymentBinding.btnAddPayment.setOnClickListener(this)
        val extras = intent.extras
        planId = (extras!!.getString("planId"))!!
        amount = (extras!!.getString("amount"))!!

        if (amount.isNotEmpty()&& amount != "0.0"){
            activityPaymentBinding.etAmount.isEnabled = false
            activityPaymentBinding.etAmount.setText(amount)
        }else{
            activityPaymentBinding.etAmount.isEnabled = true
        }
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.toolbar_back ->{
                finish()
            }
            R.id.btn_add_payment -> {
                startProgressDialog()
                val mJsonObject = JsonObject()
                mJsonObject.addProperty("planId", planId)
                mJsonObject.addProperty("userId", sharedPrefClass.getPrefValue(this, GlobalConstants.USERID).toString())
                mJsonObject.addProperty("amount", amount)
                addTokenViewModel.hitPaymentApi(this,mJsonObject)
            }

        }
    }

    override fun onPaymentSuccess(response: CommonModel) {
        stopProgressDialog()
     finish()
    }

    override fun onPaymentFailed(message: String) {
        stopProgressDialog()
       showToastError(message)
    }


}