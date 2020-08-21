package com.customvideocalling.views

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.FileProvider
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.customvideocalling.R
import com.customvideocalling.application.MyApplication
import com.customvideocalling.common.UtilsFunctions
import com.customvideocalling.constants.GlobalConstants
import com.customvideocalling.databinding.ActivityProfileBinding
import com.customvideocalling.databinding.ActivityProfileNewBinding
import com.customvideocalling.model.LoginResponse
import com.customvideocalling.model.ProfileResponse
import com.customvideocalling.utils.*
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.ProfileViewModel
import com.google.gson.JsonObject


class ProfileActivity : BaseActivity() {
    private var imageSelected = 0
    private lateinit var profileBinding: ActivityProfileNewBinding
    private lateinit var profieViewModel: ProfileViewModel
    private var mResponse: MutableLiveData<ProfileResponse>? = null
    private var sharedPrefClass: SharedPrefClass? = null
    private var confirmationDialog: Dialog? = null
    private var mDialogClass = DialogClass()
    private val mJsonObject = JsonObject()
    private val RESULT_LOAD_IMAGE = 100
    private val CAMERA_REQUEST = 1888
    private var profileImage = ""
    private var licenseImage = ""
    private var otherImage = ""
    override fun getLayoutId(): Int {
        return R.layout.activity_profile_new
    }

    override fun initViews() {
        profileBinding = viewDataBinding as ActivityProfileNewBinding
        profieViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        profileBinding.profileViewModel = profieViewModel
       // profileBinding.commonToolBar.txtEdit.visibility = View.VISIBLE
        //profileBinding.commonToolBar.imgRight.setImageResource(R.drawable.ic_nav_edit_icon)
        profileBinding.commonToolBar.toolbarText.text =
            resources.getString(R.string.view_profile)

       // makeEnableDisableViews(false)
        mJsonObject.addProperty(
            "id", "id"/* sharedPrefClass!!.getPrefValue(
                MyApplication.instance,
                GlobalConstants.USERID
            ).toString()*/
        )
        val userid = SharedPrefClass().getPrefValue(
            MyApplication.instance,
            GlobalConstants.USERID
        ).toString()

        if (UtilsFunctions.isNetworkConnected()) {
            startProgressDialog()
            profieViewModel.getProfileDetail(userid)
        }

        profieViewModel.getDetail().observe(this,
            Observer<ProfileResponse> { response ->
                stopProgressDialog()
                if (response != null) {
                    val message = response.message
                    when {
                        response.code == 200 -> {

                            setData(response)
                          //  profileBinding.profileModel = response.data!!.userDetail
                           // profileBinding.etCode.setText(response.data!!.countryCodename)
                           /* Glide.with(this)
                                .load(response.data?.licenseImage)
                                .placeholder(R.drawable.ic_user)
                                .into(profileBinding.imgLicense)
                            Glide.with(this)
                                .load(response.data?.otherimage)
                                .placeholder(R.drawable.ic_user)
                                .into(profileBinding.imgOther)*/
                        }

                        else -> showToastError(message)
                    }

                }
            })




        profieViewModel.isClick().observe(
            this, Observer<String>(function =
            fun(it: String?) {
                when (it) {
                    "imgLicense" -> {
                      /*  imageSelected = 1
                        if (checkAndRequestPermissions()) {
                            confirmationDialog =
                                mDialogClass.setUploadConfirmationDialog(this, this, "gallery")
                        }*/
                    }
                    "imgOther" -> {
                       /* imageSelected = 2
                        if (checkAndRequestPermissions()) {
                            confirmationDialog =
                                mDialogClass.setUploadConfirmationDialog(this, this, "gallery")
                        }*/
                    }
                    "txt_edit" -> {
                      /*  // isEditable = true
                        profileBinding.commonToolBar.imgToolbarText.text =
                            resources.getString(R.string.edit_profile)
                        makeEnableDisableViews(true)*/
                    }
                    "iv_edit" -> {
                      /*  imageSelected = 0
                        if (checkAndRequestPermissions()) {
                            confirmationDialog =
                                mDialogClass.setUploadConfirmationDialog(this, this, "gallery")
                        }*/

                    }


                }
            })
        )

    }

    private fun setData(respone : ProfileResponse) {
        profileBinding.tvSummary.setText(respone.data!!.userDetail!!.summery)
        profileBinding.etFirstname.setText(respone.data!!.userDetail!!.fName)
        profileBinding.etLastname.setText(respone.data!!.userDetail!!.lName)
        profileBinding.etBirthday.setText(respone.data!!.userDetail!!.dob)

        profileBinding.etInfo.setText(respone.data!!.userDetail!!.info)
        profileBinding.etGrade.setText(respone.data!!.userDetail!!.grade)
        profileBinding.etNeeded.setText(respone.data!!.userDetail!!.needed)
        profileBinding.etHelp.setText(respone.data!!.userDetail!!.help)
        profileBinding.etSuperhero.setText(respone.data!!.userDetail!!.superhero)
        profileBinding.etEmail.setText(respone.data!!.email)
        profileBinding.etPhone.setText(respone.data!!.userDetail!!.info)



    }

    private fun showError(textView: TextView, error: String) {
        textView.requestFocus()
        textView.error = error
    }


}
