package com.customvideocalling.views

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.R
import com.customvideocalling.databinding.ActivityMainBinding
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.MainViewModel
import com.customvideocalling.views.fragment.HomeFragment


class MainActivity : BaseActivity() {
    private  var activityMainBinding: ActivityMainBinding?=null
    var mainViewModel: MainViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        activityMainBinding = viewDataBinding as ActivityMainBinding
        replaceFragment()
    }


    fun replaceFragment(){
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, HomeFragment(), "")
        ft.commit()
    }
}
