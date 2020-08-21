package com.customvideocalling.views

import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProviders
import com.customvideocalling.R
import com.customvideocalling.adapters.MyViewPagerAdapter
import com.customvideocalling.adapters.MyViewTeacherPagerAdapter
import com.customvideocalling.databinding.ActivityMainBinding
import com.customvideocalling.databinding.ActivityTeacherMainBinding
import com.customvideocalling.utils.core.BaseActivity
import com.customvideocalling.viewmodels.MainViewModel
import com.customvideocalling.views.fragment.HomeFragment
import com.customvideocalling.views.fragment.JobRequestsFragment
import com.customvideocalling.views.fragment.StudentHistoryFragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.layout_custom_alert.*


class TeacherMainActivity : BaseActivity() {
    private  var activityTeacherMainBinding: ActivityTeacherMainBinding?=null
    var mainViewModel: MainViewModel? = null

    override fun getLayoutId(): Int {
        return R.layout.activity_teacher_main
    }

    override fun initViews() {
        activityTeacherMainBinding = viewDataBinding as ActivityTeacherMainBinding
        activityTeacherMainBinding!!.commonToolBar.toolbarText.text=
        resources.getString(R.string.home)
        activityTeacherMainBinding!!.tabs.addTab(activityTeacherMainBinding!!.tabs!!.newTab().setText("Active"))
        activityTeacherMainBinding!!.tabs.addTab(activityTeacherMainBinding!!.tabs!!.newTab().setText("History"))
        activityTeacherMainBinding!!.tabs.tabGravity = TabLayout.GRAVITY_CENTER

        val adapter = MyViewTeacherPagerAdapter(this, supportFragmentManager, activityTeacherMainBinding!!.tabs.tabCount)
        activityTeacherMainBinding!!.pager.adapter = adapter

        activityTeacherMainBinding!!.pager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(activityTeacherMainBinding!!.tabs))

        activityTeacherMainBinding!!.tabs.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                activityTeacherMainBinding!!.pager.currentItem = tab.position
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
