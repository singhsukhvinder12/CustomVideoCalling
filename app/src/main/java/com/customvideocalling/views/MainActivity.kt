package com.customvideocalling.views

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.R
import com.customvideocalling.adapters.MyViewPagerAdapter
import com.customvideocalling.databinding.ActivityMainBinding
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.MainViewModel
import com.customvideocalling.views.fragment.HomeFragment
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.fragment.StudentHistoryFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.layout_custom_alert.*


class MainActivity : BaseActivity() {
    private  var activityMainBinding: ActivityMainBinding?=null
    var mainViewModel: MainViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun initViews() {
        activityMainBinding = viewDataBinding as ActivityMainBinding
        activityMainBinding!!.commonToolBar.toolbarText.text=
        resources.getString(R.string.home)
        activityMainBinding!!.tabs.addTab(activityMainBinding!!.tabs!!.newTab().setText("Active"))
        activityMainBinding!!.tabs.addTab(activityMainBinding!!.tabs!!.newTab().setText("History"))
        activityMainBinding!!.tabs.tabGravity = TabLayout.GRAVITY_CENTER

        val adapter = MyViewPagerAdapter(this, supportFragmentManager, activityMainBinding!!.tabs.tabCount)
        activityMainBinding!!.pager.adapter = adapter

        activityMainBinding!!.pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(activityMainBinding!!.tabs))

        activityMainBinding!!.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                activityMainBinding!!.pager.currentItem = tab.position
            }
            override fun onTabUnselected(tab: TabLayout.Tab) {

            }
            override fun onTabReselected(tab: TabLayout.Tab) {

            }
        })

       // replaceFragment()
    }


   /* fun replaceFragment(){
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.frame_layout, StudentHistoryFragment(), "")
        ft.commit()
    }*/
}
